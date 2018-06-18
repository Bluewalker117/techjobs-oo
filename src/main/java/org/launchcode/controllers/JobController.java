package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobFieldData;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        Job single = jobData.findById(id);
        model.addAttribute("name", single.getName());
        model.addAttribute("employer", single.getEmployer());
        model.addAttribute("skill", single.getCoreCompetency());
        model.addAttribute("location", single.getLocation());
        model.addAttribute("jobtype", single.getPositionType());
        model.addAttribute("title", "Test");


        // 2-do #1 get the Job with the given ID and pass it into the view

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // 2-do #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        model.addAttribute("name", jobForm.getName());
        model.addAttribute("employer", jobData.getEmployers().findById(jobForm.getEmployerId()));
        model.addAttribute("skill", jobData.getCoreCompetencies().findById(Integer.parseInt(jobForm.getCoreCompetency().getValue())));
        model.addAttribute("jobtype", jobData.getPositionTypes().findById(Integer.parseInt(jobForm.getPositionType().getValue())));
        model.addAttribute("location", jobData.getLocations().findById(Integer.parseInt(jobForm.getLocation().getValue())));




        return "job-detail";


    }
}
