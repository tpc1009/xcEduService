package com.xuecheng.api.file;

import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author tpc
 * @date 2020/3/27 20:36
 */
@Api(value="file配置管理接口",description = "文件配置管理接口，提供数据模型的管理、查询接口")
public interface FileSystemControllerApi {

    @ApiOperation(value="上传文件")
    public UploadFileResult upload(MultipartFile multipartFile, String filetag, String businesskey, String metadata);

}
