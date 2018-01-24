package com.myspring.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myspring.anno.Autowried;
import com.myspring.anno.Component;
import com.myspring.util.ScanUtils;

public class MySpringBeans {

	private static final Map<String, Object> BEAN_MAP = new HashMap<>();
	private static final String packagePath = "com.myspring";// 扫描包的路径

	public MySpringBeans() throws Exception {
		init();
	}

	public List<Class<?>> scanPackage(String packagePath) throws Exception {
		return ScanUtils.searchClass(packagePath).stream().collect(ArrayList::new, (list, item) -> {
			try {
				Class<?> cla = Class.forName(item);
				if (cla.getDeclaredAnnotation(Component.class) != null) {
					list.add(cla);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}, (list1, list2) -> list1.addAll(list2));
	}

	// 初始化
	public void init() throws Exception {
		List<Class<?>> classes = scanPackage(packagePath);
		if (classes != null && !classes.isEmpty()) {
			for (Class<?> cla : classes) {// 第一步先将所有的bean都初始化
				BEAN_MAP.put(cla.getSimpleName(), cla.newInstance());
			}
			for (Class<?> cla : classes) {// 第二部建立bean之间的对应关系
				Field[] fields = cla.getDeclaredFields();
				if (fields != null && fields.length > 0) {
					for (Field field : fields) {
						Autowried auto = field.getAnnotation(Autowried.class);
						if (auto != null) {
							field.setAccessible(true);
							field.set(BEAN_MAP.get(cla.getSimpleName()), BEAN_MAP.get(field.getType().getSimpleName()));
						}
					}
				}
			}
		}
	}

	public Object getBeanByClassName(String beanName) {
		return BEAN_MAP.get(beanName);
	}

	public Map<String, Object> getAllBeans() {
		return BEAN_MAP;
	}

}
