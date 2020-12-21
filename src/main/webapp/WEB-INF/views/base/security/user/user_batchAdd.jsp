<%--
  Created by IntelliJ IDEA.
  User: YuYuYu
  Date: 16-10-11
  Time: 下午2:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',border:false" style="width: 100%;height: 60px;" title="下载模板">
        <select id="importType" class="pull-right" name="type">
            <option value="119" selected>用户账号信息模板</option>
        </select>
        <a href="javascript:output()" class="easyui-linkbutton" iconCls="icon-download" plain="false">模板下载</a>
        <iframe id="down_temp_iframe" style="display: none;"></iframe>
    </div>
    <!--弱覆盖指标列表-->
    <div data-options="region:'center',border:false"style="width: 100%;height: 100%" title="上传数据">
        <input type="file" name="uploadify" id="uploadify" />
        <a href="javascript:$('#uploadify').uploadify('upload')" class="easyui-linkbutton" iconCls="icon-upload"  >导入</a>
        <a href="javascript:$('#uploadify').uploadify('cancel')" class="easyui-linkbutton" iconCls="icon-undo"  >取消导入</a> <br/><br/>
        说明：</br>
        1.请上传CSV类型文件<br/>
        2.请确保文件编码为UTF-8编码格式  <br/>
        3.火狐浏览器无法使用上传功能，请用谷歌浏览器
        4.CSV中数据从第二行开始导入到数据库
     </div>
</div>