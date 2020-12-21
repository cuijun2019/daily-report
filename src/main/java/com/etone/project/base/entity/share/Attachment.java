/*
 * Copyright 2010 Guangdong Eastone Century Technology Co.,Ltd.
 * All rights reserved.
 */
package com.etone.project.base.entity.share;

import com.etone.ee.modules.data.Meta;
import com.etone.project.base.entity.Base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 附件表
 *
 * @author <a href="mailto:maxid@qq.com">ZengJun</a>
 * @version $$Revision: 14148 $$
 */
@Entity
@Table(name = "tbAttachment")
public class Attachment extends Base {
    // members
    private static final long serialVersionUID = 5419970149154050544L;

    @Meta(name = "名称", exportable = true)
    @Column(name = "vcFileName", length = 200)
    public String fileName;

    @Meta(name = "类型", exportable = true)
    @Column(name = "vcFileType", length = 10)
    public String fileType;

    @Meta(name = "大小(M)", exportable = true)
    @Column(name = "bigFileSize")
    public long fileSize = 0;

    @Meta(name = "存储路径", exportable = true)
    @Column(name = "vcPath", length = 400)
    public String path;
    // static block

    // constructors

    // properties

    // public methods

    // protected methods

    // friendly methods

    // private methods

    // inner class

    // test main
}
