package org.shield.admin.controller;

import org.shield.admin.model.Terms;
import org.shield.admin.service.TermsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *
 * 条款
 *
 * @author zacksleo@gmail.com
 */
@Api(tags = "App: 条款")
@Controller("AppTermsController")
@RequestMapping("app/terms")
public class AppTermsController {

    @Autowired
    private TermsService service;

    @ApiOperation(value = "详情页面", produces = "text/html")
    @GetMapping("/{termsId}")
    public String view(@PathVariable String termsId, Model model) {
        Terms terms = service.findById(termsId);
        if (terms == null || !terms.getIsActive()) {
            return "terms-not-found";
        }
        model.addAttribute("name", terms.getName());
        model.addAttribute("content", terms.getContent());
        return "terms";
    }
}
