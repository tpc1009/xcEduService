package com.xuecheng.manage_cms.dao;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author tpc
 * @date 2020/3/19 19:22
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GridFsTest {

    @Autowired
    private GridFSBucket gridFSBucket;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Test
    public void queryFile() throws IOException {
        String file = "5b9c54e264c614237c271a99";

        //根据文件id查询
        GridFSFile gridfile = this.gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(file)));

        //打开下载流对象
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridfile.getObjectId());

        //创建gridFsResource,用于获取流对象
        GridFsResource gridFsResource = new GridFsResource(gridfile, gridFSDownloadStream);

        //获取流中数据
        String tostring = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");

        System.out.println(tostring);


    }


}
