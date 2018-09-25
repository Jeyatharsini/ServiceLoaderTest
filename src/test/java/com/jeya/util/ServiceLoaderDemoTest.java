package com.jeya.util;

import static org.fest.reflect.core.Reflection.method;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.Test;

@PrepareForTest(ServiceLoaderDemo.class)
public class ServiceLoaderDemoTest extends PowerMockTestCase {
	private ServiceLoaderDemo serviceLoaderDemo = new ServiceLoaderDemo();

	private ServiceLoader mockServiceLoader;
	
	@Test
	public void testIterate() {
		mockServiceLoader = PowerMockito.mock(ServiceLoader.class);
		CPServiceImplTwo cpServiceImplTwo = new CPServiceImplTwo();
		CPServiceImplOne cpServiceImplOne = new CPServiceImplOne();

		List<CPService> mutableList = new ArrayList<>();
		mutableList.add(cpServiceImplTwo);
		mutableList.add(cpServiceImplOne);
		
		List<CPService> immutableList = Collections.unmodifiableList(mutableList);
		
		when(mockServiceLoader.iterator()).thenReturn(immutableList.iterator());
		CPService methodToTest = null;
		try {
			methodToTest = method("iterate").withReturnType(CPService.class).withParameterTypes(ServiceLoader.class)
					.in(serviceLoaderDemo).invoke(mockServiceLoader);
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		assertEquals(methodToTest.getClass(), CPServiceImplOne.class);
	}
}