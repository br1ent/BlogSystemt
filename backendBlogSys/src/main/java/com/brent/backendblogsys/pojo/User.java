package com.brent.backendblogsys.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String email;
    private String username;
    @TableField(select = false)
    private String password;
    private String photo;
    private Integer isValued;
    private Integer status;
    /**
     * 创建时间
     * fill = FieldFill.INSERT 表示插入时自动填充
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 更新时间
     * fill = FieldFill.INSERT_UPDATE 表示插入和修改时自动填充
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
