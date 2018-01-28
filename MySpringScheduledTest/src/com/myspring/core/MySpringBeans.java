package com.myspring.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.myspring.anno.Autowried;
import com.myspring.anno.Component;
import com.myspring.anno.Scheduled;
import com.myspring.util.ScanUtils;

public class MySpringBeans {

	private static final Map<String, Object> BEAN_MAP = new HashMap<>();
	private static final String packagePath = "com.myspring";// 扫描包的路径
	private static final ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);

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
			for (Class<?> cla : classes) {// 第二步建立bean之间的对应关系
				Field[] fields = cla.getDeclaredFields();
				Method[] methods = cla.getDeclaredMethods();
				if (fields != null && fields.length > 0) {
					for (Field field : fields) {
						Autowried auto = field.getDeclaredAnnotation(Autowried.class);
						if (auto != null) {
							field.setAccessible(true);
							field.set(BEAN_MAP.get(cla.getSimpleName()), BEAN_MAP.get(field.getType().getSimpleName()));
						}
					}
				}
				if (methods != null && methods.length > 0) {
					for (Method method : methods) {
						Scheduled sche = method.getDeclaredAnnotation(Scheduled.class);
						if (sche != null) {
							method.setAccessible(true);							
							ses.scheduleAtFixedRate(
									()->{
										try {
											method.invoke(BEAN_MAP.get(cla.getSimpleName()));
										} catch (Exception e) {
											e.printStackTrace();
										}
									}, 
									sche.initialDelay(),
									sche.period(),
									sche.unit());
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
