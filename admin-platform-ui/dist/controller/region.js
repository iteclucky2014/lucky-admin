/** layuiAdmin.pro-v1.1.0 LPPL License By http://www.layui.com/admin/ */
 ;layui.define(["table","form","laydate","treeSelect"],function(e){var t=layui.$,a=layui.admin,l=layui.view,n=layui.table,i=layui.treeSelect,o=layui.form,r=layui.laydate,c=layui.data(layui.setter.tableName);i.render({elem:"#deptName",data:"/platform/dept/getDeptTreeData?access_token="+c.access_token,type:"get",placeholder:"修改默认提示信息",search:!0,click:function(e){console.log(e.current.id),t("#belongRegion").val(e.current.id)},success:function(e){console.log(e)}}),o.render(null,"app-content-list"),o.on("submit(LAY-app-contlist-search)",function(e){var t=e.field;n.reload("LAY-app-content-list",{where:t})}),n.render({elem:"#LAY-app-content-list",url:"/platform/device/page?access_token="+c.access_token,cellMinWidth:80,toolbar:"#test-table-toolbar-toolbarDemo",cols:[[{type:"checkbox",fixed:"left"},{field:"code",title:"编号",width:100,sort:!0},{field:"standard",title:"规格",width:200,sort:!0},{field:"manuFacturer",title:"生产厂家",width:100,sort:!0},{field:"installDate",title:"安装日期",width:200,sort:!0},{field:"installLocation",title:"安装位置",width:200},{field:"alarmRules",title:"报警规则",sort:!0},{field:"belongRegion",title:"隶属分区",sort:!0},{title:"操作",minWidth:150,align:"center",fixed:"right",toolbar:"#table-content-list"}]],page:!0,limit:10,limits:[10,15,20,25,30],text:{none:"一条数据也没有^_^"}}),n.on("toolbar(LAY-app-content-list)",function(e){var t=n.checkStatus("LAY-app-content-list"),i=t.data;switch(e.event){case"batchdel":if(0===i.length)return layer.msg("请选择数据");layer.confirm("确定删除吗？",function(e){n.reload("LAY-app-content-list"),layer.msg("已删除")});break;case"add":e.data;a.popup({title:"添加文章",area:["600px","750px"],id:"LAY-popup-content-add",success:function(e,t){l(this.id).render("assetManagement/regionform").done(function(){r.render({elem:"#testlaydatenormalcn"}),o.render(null,"layuiadmin-app-form-list"),o.on("submit(layuiadmin-app-form-submit)",function(e){e.field;layui.table.reload("LAY-app-content-list"),layer.close(t)})})}})}}),n.on("tool(LAY-app-content-list)",function(e){var t=e.data;"del"===e.event?layer.confirm("确定删除此分区？",function(t){e.del(),layer.close(t)}):"edit"===e.event&&a.popup({title:"编辑",area:["600px","750px"],id:"LAY-popup-content-edit",success:function(e,a){l(this.id).render("assetManagement/regionform",t).done(function(){r.render({elem:"#testlaydatenormalcn"}),o.render(null,"layuiadmin-app-form-list"),o.on("submit(layuiadmin-app-form-submit)",function(e){e.field;layui.table.reload("LAY-app-content-list"),layer.close(a)})})}})}),e("region",{})});