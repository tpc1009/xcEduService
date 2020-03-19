package com.xuecheng.manage_cms.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-09-12 18:32
 **/
@Service
public class PageService {

    @Autowired
    private CmsPageRepository cmsPageRepository;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;


    @Autowired
    private GridFsTemplate gridFsTemplate;


    @Autowired
    private GridFSBucket gridFSBucket;


    /**
     * 页面查询方法
     * @param page 页码，从1开始记数
     * @param size 每页记录数
     * @param queryPageRequest 查询条件
     * @return
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest){

        if(queryPageRequest == null){
             queryPageRequest = new QueryPageRequest();
        }

        CmsPage cmsPage = new CmsPage();
        //精确查询
        if(StringUtils.isNotEmpty(queryPageRequest.getSiteId())){
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        if(StringUtils.isNotEmpty(queryPageRequest.getTemplateId())){
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }
        if(StringUtils.isNotEmpty(queryPageRequest.getPageAliase())){
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        //设置条件查询
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());//模糊查询

        //分页参数
        if(page <=0){
            page = 1;
        }
        page = page -1;
        if(size<=0){
            size = 10;
        }
        Pageable pageable = PageRequest.of(page,size);

        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);
        Page<CmsPage> all = cmsPageRepository.findAll(example,pageable);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());//数据列表
        queryResult.setTotal(all.getTotalElements());//数据总记录数
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        return queryResponseResult;
    }

//**********************************************************************************************************************
    public CmsPageResult add(CmsPage cmsPage){
        CmsPage byPageNameAndSiteIdAndPageWebPath = this.cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if(byPageNameAndSiteIdAndPageWebPath != null){
            cmsPage.setPageId(null);
            this.cmsPageRepository.save(cmsPage);
            return new CmsPageResult(CommonCode.SUCCESS,cmsPage);
        }
        return new CmsPageResult(CommonCode.FAIL,null);
    }

//**********************************************************************************************************************
    //根据id查询
    public CmsPage getById(String id){
        Optional<CmsPage> byId = this.cmsPageRepository.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }
        return null;
    }
//**********************************************************************************************************************
    //修改
    public CmsPageResult udpate(String id,CmsPage cmsPage){
        CmsPage one = this.getById(id);
        if(one != null){
            one.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            one.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            one.setPageName(cmsPage.getPageName());
            //更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //更新url
            one.setDataUrl(cmsPage.getDataUrl());
            //执行更新
            CmsPage save = this.cmsPageRepository.save(one);
            if(save !=null){
                return new CmsPageResult(CommonCode.SUCCESS,save);
            }
        }
        return new CmsPageResult(CommonCode.FAIL,null);
    }

//*********************************************************************************************************************
    //根据id删除
    public ResponseResult delete(String id){
        Optional<CmsPage> one = this.cmsPageRepository.findById(id);
        if(one.isPresent()){
            this.cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }


//*********************************************************************************************************************
    //静态页面集成
    public String getPageHtml(String id){

        //1.获取数据模型
        Map model = getModelByPageId(id);
        if(model == null){
            //获取模型数据为空
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }

        //2.获取模板
        String tempContent =  getTemplateByPageId(id);
        if(StringUtils.isEmpty(tempContent)){
            //模板为空
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }

        //3.执行静态化
        String html = generateHtml(tempContent,model);
        return null;
    }

    //执行静态化
    private String generateHtml(String tempContent, Map model) {
        try {
        //生成配置类
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
        //模板加载器
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template",tempContent);
        //配置模板加载器
        configuration.setTemplateLoader(stringTemplateLoader);
        //获取模板
        Template template = configuration.getTemplate("template");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        return html;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //获取模板
    private String getTemplateByPageId(String id) {

        CmsPage cms = this.getById(id);
        if(cms == null){
            //页面不存在
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        //获取template模板id
        String templateId = cms.getTemplateId();
        if(StringUtils.isEmpty(templateId)){
            //模板页面为空
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        //查询模板模型
        Optional<CmsTemplate> opt = this.cmsTemplateRepository.findById(templateId);
        if(opt.isPresent()){
            CmsTemplate cmsTemplate = opt.get();
            //模板文件id
            String templateFileId = cmsTemplate.getTemplateFileId();

            //获取模板文件内容
            GridFSFile gridFile = this.gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));

            //打开流对象
            GridFSDownloadStream gridFSDownloadStream = this.gridFSBucket.openDownloadStream(gridFile.getObjectId());

            //创建gridFsResource对象
            GridFsResource gridFsResource = new GridFsResource(gridFile, gridFSDownloadStream);

            try {
                String content = IOUtils.toString(gridFSDownloadStream, "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
            return null;
    }

    //获取数据模型
    private Map getModelByPageId(String id) {

        CmsPage cms = this.getById(id);
        String dataUrl = cms.getDataUrl();
        if(StringUtils.isEmpty(dataUrl)){
            //页面不存咋
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        //根据dataUrl获取模板模型
        ResponseEntity<Map> forEntity = this.restTemplate.getForEntity(dataUrl, Map.class);
        Map body = forEntity.getBody();
        return body;
    }

}
