package com.atguigu.base;

import org.springframework.ui.Model;

/**
 * Date : 2022/5/17
 * Author : cc
 * Description :
 */
public class BaseController {

    private final static String PAGE_SUCCESS = "common/successPage";

    public String successPage(Model model,String successPage){

        model.addAttribute("messagePage",successPage);

        return PAGE_SUCCESS;

    }


}
