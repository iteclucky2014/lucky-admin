/**

 @Name：layuiAdmin 用户管理 管理员管理 角色管理 部门管理
 @Author：star1029
 @Site：http://www.layui.com/admin/
 @License：LPPL
    
 */


layui.define(['table', 'treetable', 'form'], function(exports){
  var $ = layui.$
  ,admin = layui.admin
  ,view = layui.view
  ,table = layui.table
  ,treetable = layui.treetable
  ,form = layui.form;

    var localData = layui.data(layui.setter.tableName);

  //用户管理
  table.render({
    elem: '#LAY-user-manage'
    ,url: './json/useradmin/webuser.js' //模拟接口
    ,cols: [[
      {type: 'checkbox', fixed: 'left'}
      ,{field: 'id', width: 100, title: 'ID', sort: true}
      ,{field: 'username', title: '用户名', minWidth: 100}
      ,{field: 'avatar', title: '头像', width: 100, templet: '#imgTpl'}
      ,{field: 'phone', title: '手机'}
      ,{field: 'email', title: '邮箱'}
      ,{field: 'sex', width: 80, title: '性别'}
      ,{field: 'ip', title: 'IP'}
      ,{field: 'jointime', title: '加入时间', sort: true}
      ,{title: '操作', width: 150, align:'center', fixed: 'right', toolbar: '#table-useradmin-webuser'}
    ]]
    ,page: true
    ,limit: 30
    ,height: 'full-320'
    ,text: '对不起，加载出现异常！'
  });
  
  //监听工具条
  table.on('tool(LAY-user-manage)', function(obj){
    var data = obj.data;
    if(obj.event === 'del'){
      layer.prompt({
        formType: 1
        ,title: '敏感操作，请验证口令'
      }, function(value, index){
        layer.close(index);
        
        layer.confirm('真的删除行么', function(index){
          obj.del();
          layer.close(index);
        });
      });
    } else if(obj.event === 'edit'){
      admin.popup({
        title: '编辑用户'
        ,area: ['500px', '450px']
        ,id: 'LAY-popup-user-add'
        ,success: function(layero, index){
          view(this.id).render('user/user/userform', data).done(function(){
            form.render(null, 'layuiadmin-form-useradmin');
            
            //监听提交
            form.on('submit(LAY-user-front-submit)', function(data){
              var field = data.field; //获取提交的字段

              //提交 Ajax 成功后，关闭当前弹层并重载表格
              //$.ajax({});
              layui.table.reload('LAY-user-manage'); //重载表格
              layer.close(index); //执行关闭 
            });
          });
        }
      });
    }
  });


    //管理员管理
    table.render({
        elem: '#LAY-user-back-manage'
        ,url: '/platform/user/page?access_token='+localData["access_token"] //模拟接口
        ,cols: [[
            {type: 'checkbox', fixed: 'left'}
            ,{field: 'id', width: 80, title: 'ID', sort: true}
            ,{field: 'username', title: '登录名', sort: true}
            ,{field: 'password', title: '密码'}
            ,{field: 'realName', title: '姓名'}
            ,{field: 'dept', title: '部门',
                templet: function(d) {
                    if (d.dept) {
                        return d.dept.deptName;
                    } else {
                        return '';
                    }
                }
            }
            ,{field: 'tel', title: '手机'}
            ,{field: 'authorities', title: '拥有角色',
                templet: function(d) {
                    var roles = d.authorities || [];
                    return roles.map(function(role) {
                        return role.roleName;
                    }).join(',');
                }
            }
            ,{title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#table-useradmin-admin'}
        ]]
        ,text: '对不起，加载出现异常！'
        ,page: true
    });

    //监听工具条
    table.on('tool(LAY-user-back-manage)', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
            layer.confirm('确定删除此用户吗？', function(index){
                var ids = [];
                ids.push(data.id);
                var loading = layer.msg('处理中', { icon: 16 ,shade: 0.01 ,time: 0});
                $.ajax({
                    url: '/platform/user/delete?access_token='+localData["access_token"]
                    ,type: 'POST'
                    ,contentType: 'application/json; charset=utf-8'
                    ,data: JSON.stringify({
                        userid: '10001',
                        username: 'admin',
                        data: ids
                    })
                    ,dataType: 'json'
                    ,success: function(resp){
                        if (resp.code === 0) {
                            layer.close(loading); //执行关闭
                            table.reload('LAY-user-back-manage');
                            layer.msg('已删除');
                        } else {
                            layer.msg(resp.msg, {icon: 6});
                        }
                    }
                    ,error: function(resp) {
                        layer.msg(resp.msg, {icon: 6});
                    }
                });
            });
        } else if(obj.event === 'edit'){
            $.getJSON('/platform/role/page?page=1&limit=100&access_token='+localData["access_token"], function(rep){
                admin.popup({
                    title: '编辑管理员'
                    ,area: ['500px', '600px']
                    ,id: 'LAY-popup-user-add'
                    ,success: function(layero, index){
                        view(this.id).render('user/administrators/adminform', data).done(function(){
                            form.render(null, 'layuiadmin-form-admin');

                            // 动态生成角色复选框
                            var roles = rep.data || [];
                            roles.forEach(function(item) {
                                $("#LAY-popup-user-add-roles-checkbox").append('<input type="checkbox" name="platformRoles[]" title="'+ item.roleName +'" value="'+ item.id +'" lay-skin="primary">');
                            });

                            // 复选框初始化
                            var platformRoleIds = [];
                            data.authorities.forEach(function(item) {
                                platformRoleIds.push(item.id);
                            });
                            $("#LAY-popup-user-add-roles-checkbox input").each(function() {
                                if ($.inArray(parseInt($(this).val()), platformRoleIds) != -1 ) {
                                    $(this).prop("checked", true);
                                } else {
                                    $(this).prop("checked", false);
                                }
                            });
                            form.render('checkbox');
                            //监听提交
                            form.on('submit(LAY-user-back-submit)', function(data){
                                var field = data.field; //获取提交的字段
                                // 整理复选框数据
                                var platformRoles = [];
                                for (var key in field) {
                                    if (key.indexOf('platformRoles[') != -1) {
                                        platformRoles.push(field[key]);
                                    }
                                }
                                field['platformRoles'] = platformRoles;
                                var loading = layer.msg('处理中', { icon: 16 ,shade: 0.01, time: 0 });
                                $.ajax({
                                    url: '/platform/user/save?access_token='+localData["access_token"]
                                    ,type: 'POST'
                                    ,contentType: 'application/json; charset=utf-8'
                                    ,data: JSON.stringify({
                                        userid: '10001',
                                        username: 'admin',
                                        data: field
                                    })
                                    ,dataType: 'json'
                                    ,success: function(resp){
                                        if (resp.code === 0) {
                                            layer.close(loading); //执行关闭
                                            layui.table.reload('LAY-user-back-manage'); //重载表格
                                            layer.close(index); //执行关闭
                                        } else {
                                            layer.msg(resp.msg, {icon: 6});
                                        }
                                    }
                                    ,error: function(resp) {
                                        layer.msg(resp.msg, {icon: 6});
                                    }
                                });

                            });
                        });
                    }
                });
            });


        }
    });

    //角色管理
    table.render({
        elem: '#LAY-user-back-role'
        ,url: '/platform/role/page?page=1&limit=100&access_token='+localData["access_token"] //模拟接口
        ,cols: [[
            {type: 'checkbox', fixed: 'left'}
            ,{field: 'role', title: '角色标识', sort: true}
            ,{field: 'roleName', title: '角色名称'}
            ,{field: 'platformPermissions', title: '拥有权限',
                templet: function(d) {
                    var permissions = d.platformPermissions || [];
                    return permissions.map(function(permission) {
                        return permission.name;
                    }).join(',');
                }
            }
            ,{field: 'roleDesc', title: '权限描述'}
            ,{title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#table-useradmin-admin'}
        ]]
        ,text: '对不起，加载出现异常！'
    });

    //监听工具条
    table.on('tool(LAY-user-back-role)', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
            layer.confirm('确定删除此角色？', function(index){
                var ids = [];
                ids.push(data.id);

                var loading = layer.msg('处理中', { icon: 16 ,shade: 0.01 });
                $.ajax({
                    url: '/platform/role/delete?access_token='+localData["access_token"]
                    ,type: 'POST'
                    ,contentType: 'application/json; charset=utf-8'
                    ,data: JSON.stringify({
                        userid: '10001',
                        username: 'admin',
                        data: ids
                    })
                    ,dataType: 'json'
                    ,success: function(resp){
                        if (resp.code === 0) {
                            layer.close(loading); //执行关闭
                            layer.close(index); //执行关闭
                            layer.msg('已删除');
                            layui.table.reload('LAY-user-back-role'); //重载表格
                        } else {
                            layer.msg(resp.msg, {icon: 6});
                        }
                    }
                    ,error: function(resp) {
                        layer.msg(resp.msg, {icon: 6});
                    }
                });

            });
        }else if(obj.event === 'edit'){

            $.getJSON('/platform/permission/page?page=1&limit=100&access_token='+localData["access_token"], function(rep){
                //console.log(rep);
                admin.popup({
                    title: '编辑角色'
                    ,area: ['500px', '480px']
                    ,id: 'LAY-popup-user-edit'
                    ,success: function(layero, index){
                        view(this.id).render('user/administrators/roleform', data).done(function(){
                            form.render(null, 'layuiadmin-form-role');

                            // 权限列表复选框动态生成（全部）
                            var permissions = rep.data || [];
                            //console.log(permissions);
                            permissions.forEach(function(item){
                                $("#LAY-popup-user-edit-platformPermissions-checkbox").append('<input type="checkbox" name="platformPermissions[]" title="'+ item.name +'" value="'+ item.id +'" lay-skin="primary">');
                            });
                            //form.render('checkbox');
                            // 复选框初始化
                            var platformPermissionIds = [];
                            data.platformPermissions.forEach(function(item) {
                                platformPermissionIds.push(item.id);
                            });
                            $("#LAY-popup-user-edit-platformPermissions-checkbox input[name^='platformPermissions']").each(function() {
                                if ($.inArray(parseInt($(this).val()), platformPermissionIds) != -1 ) {
                                    $(this).prop("checked", true);
                                } else {
                                    $(this).prop("checked", false);
                                }
                            });
                            form.render('checkbox');

                            //监听提交
                            form.on('submit(LAY-user-role-submit)', function(data){
                                var field = data.field; //获取提交的字段
                                console.log(JSON.stringify(field));

                                // 整理复选框数据
                                var platformPermissions = [];
                                for (var key in field) {
                                    if (key.indexOf('platformPermissions[') != -1) {
                                        platformPermissions.push(field[key]);
                                    }
                                }
                                field['platformPermissions'] = platformPermissions;

                                //提交 Ajax 成功后，关闭当前弹层并重载表格
                                //$.ajax({});
                                var loading = layer.msg('处理中', { icon: 16 ,shade: 0.01 });
                                $.ajax({
                                    url: '/platform/role/save?access_token='+localData["access_token"]
                                    ,type: 'POST'
                                    ,contentType: 'application/json; charset=utf-8'
                                    ,data: JSON.stringify({
                                        userid: '10001',
                                        username: 'admin',
                                        data: field
                                    })
                                    ,dataType: 'json'
                                    ,success: function(resp){
                                        if (resp.code === 0) {
                                            layer.close(loading); //执行关闭
                                            layer.close(index); //执行关闭
                                            layui.table.reload('LAY-user-back-role'); //重载表格
                                        } else {
                                            layer.msg(resp.msg, {icon: 6});
                                        }
                                    }
                                    ,error: function(resp) {
                                        layer.msg(resp.msg, {icon: 6});
                                    }
                                });
                            });
                        });
                    }
                });

            });
        }
    });

    exports('useradmin', {})
});