package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobFieldData;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
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



        // 2-do #1 get the Job with the given ID and pass it into the view

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, Job newJob, @Valid JobForm jobForm, Errors errors) {

        // 2-do #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (errors.hasErrors()) {
            return "new-job";
        }

        newJob.setEmployer(jobData.getEmployers().findById(jobForm.getEmployerId()));
        newJob.setLocation(jobData.getLocations().findById(Integer.parseInt(jobForm.getLocation().getValue())));
        newJob.setPositionType(jobData.getPositionTypes().findById(Integer.parseInt(jobForm.getPositionType().getValue())));
        newJob.setCoreCompetency(jobData.getCoreCompetencies().findById(Integer.parseInt(jobForm.getCoreCompetency().getValue())));
        jobData.add(newJob);

        return "redirect:/job?id=" + newJob.getId();


    }
}
