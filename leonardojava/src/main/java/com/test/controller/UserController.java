package com.test.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tiles.request.Request;
import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.test.dao.PeopleDAOImpl;
import com.test.dao.ProjectListDAOImpl;
import com.test.entity.People;
import com.test.entity.ProjectList;

@Controller
public class UserController {
	@Autowired
	ProjectListDAOImpl projectListDAOImpl;
	@Autowired
	PeopleDAOImpl peopleDAOImpl;
	
	@RequestMapping("/home")
	public String mainView(Model model){
		List<ProjectList> projectList=projectListDAOImpl.getProjectListWithUser();
		List<ProjectList> projectListall=projectListDAOImpl.getProjectList();
		model.addAttribute("projectList", projectList);	
		model.addAttribute("projectListall", projectListall);
		
		return "main";
		
	}
	
	@RequestMapping(value="/saveprojectdetail", method=RequestMethod.POST)
	public @ResponseBody String saveProject(HttpServletRequest request, HttpServletResponse response){
		ProjectList projectList=new ProjectList();
		projectList.setProjectName(request.getParameter("projectname"));
		projectList.setProjectDescrition(request.getParameter("projectdesc"));
		projectListDAOImpl.insertProjectList(projectList);
		
		return "Project Details saved";
		
	}
	
	@RequestMapping(value="{id}/projectedit", method=RequestMethod.GET)
	public String projectList(Model model,@PathVariable("id") int id){
		ProjectList projectList=projectListDAOImpl.getProjectList(id);
		List<People> people=peopleDAOImpl.getPeopleLink(projectList);
		model.addAttribute("projectList", projectList);
		model.addAttribute("people", people);
		return "projectedit";
		
	}


	
}
