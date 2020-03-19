package com.xuecheng.manage_cms.controller;

import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

/**
 * @author tpc
 * @date 2020/3/19 21:53
 */
@Controller
@RequestMapping("/cms/preview")
public class CmsPagePreviewController extends BaseController {


    @Autowired
    private PageService pageService;


    @GetMapping("/{pageId}")
    public void preview(@PathVariable("pageId") String id) throws IOException {
        String pageHtml = this.pageService.getPageHtml(id);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(pageHtml.getBytes());
    }
}
