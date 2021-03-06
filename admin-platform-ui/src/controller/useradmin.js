/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ 加QQ：1293166442 获取源码版*/
;
layui.define(["table", "form"],
function(e) {
    var i = (layui.$, layui.admin), $ = layui.$,
    t = layui.view,
    l = layui.table, table = layui.table,
    r = layui.form;
    l.render({
        elem: "#LAY-user-manage",
        url: "/lucky/user/getUsers?access_token=" + layui.data(layui.setter.tableName)[layui.setter.request.tokenName],
        cols: [[{
            type: "checkbox",
            fixed: "left"
        },
        // {
        //     field: "id",
        //     width: 100,
        //     title: "ID",
        //     sort: !0
        // },
        {
            field: "username",
            title: "用户名",
            minWidth: 150
        },
        {
            field: "roleName",
            title: "角色"
        },
        {
            field: "nickname",
            title: "昵称"
        },
        // {
        //     field: "avatar",
        //     title: "头像",
        //     width: 100,
        //     templet: "#imgTpl"
        // },
        {
            field: "sex",
            title: "性别",
            templet: "#sex",
            width: 100
        },
        {
            field: "mobile",
            title: "手机"
        },
        {
            field: "email",
            title: "邮箱"
        },
        {
            field: "createTime",
            title: "注册时间"
            // sort: !0
        },
        {
            field: "isDelete",
            title: "状态",
            templet: "#isDelete",
            width: 100,
            align: "center"
        },
        {
            title: "操作",
            width: 200,
            align: "center",
            fixed: "right",
            toolbar: "#table-useradmin-webuser"
        }]],
        page: !0,
        limit: 50,
        height: "full-280",
        text: { none: '数据不存在！' }
    }),
    l.on("tool(LAY-user-manage)",
    function(e) {
        var l = e.data;
        "del" === e.event ? // layer.prompt({
        //     formType: 1,
        //     title: "敏感操作，请验证口令"
        // },
        // function(i, t) {
        //     layer.close(t),
            layer.confirm("确定删除吗？",
            function(i) {
                layer.close(i);

                var users = [];
                var tuser = {
                    id: l.id
                }
                users.push(tuser);

                layui.admin.req({
                    url: '/lucky/user/batchDel?access_token=' + layui.data(layui.setter.tableName)[layui.setter.request.tokenName]
                    ,type: 'POST'
                    ,contentType: 'application/json; charset=utf-8'
                    ,data: JSON.stringify({
                        data: users
                    })
                    ,success: function(res) {
                        if (res.code === layui.setter.response.statusCode.ok) {
                            //修改成功的提示与跳转
                            layer.msg(res.msg, {
                                offset: '15px'
                                , icon: 1
                                , time: 1000
                            }, function () {
                                table.reload('LAY-user-manage');
                            });
                        } else if (res.code !== layui.setter.response.statusCode.logout) {
                            layer.msg(res.msg, {
                                icon: 5
                                ,time: 1000
                            });
                        }
                    }
                    ,error: function(res) {
                        layer.msg(res, {
                            icon: 5
                            ,time: 1000
                        });
                    }
                });
            // })
        }) : "edit" === e.event && i.popup({
            title: "分配角色",
            area: ["1000px", "1350px"],
            id: "LAY-popup-user-role",
            success: function(e, i) {
                $("#roleId").val(l.roleId);
                t(this.id).render("user/user/userform", l).done(function() {
                    r.render(null, "layuiadmin-form-useradmin"),
                    r.on("submit(LAY-user-front-submit)",
                    function(e) {
                        var checkStatus = table.checkStatus('LAY-user-role'),
                            checkData = checkStatus.data; //得到选中的数据

                        if(checkData.length === 0){
                            return layer.msg('请选择角色', {
                                icon: 5
                                ,time: 1000
                            });
                        }

                        layer.close(i);

                        var tuser = {
                            id: l.id,
                            roleId: checkData[0].id
                        }

                        layui.admin.req({
                            url: '/lucky/user/disRole?access_token=' + layui.data(layui.setter.tableName)[layui.setter.request.tokenName]
                            ,type: 'POST'
                            ,contentType: 'application/json; charset=utf-8'
                            ,data: JSON.stringify({
                                data: tuser
                            })
                            ,success: function(res) {
                                if (res.code === layui.setter.response.statusCode.ok) {
                                    //修改成功的提示与跳转
                                    layer.msg(res.msg, {
                                        offset: '15px'
                                        , icon: 1
                                        , time: 1000
                                    }, function () {
                                        table.reload('LAY-user-manage');
                                    });
                                } else if (res.code !== layui.setter.response.statusCode.logout) {
                                    layer.msg(res.msg, {
                                        icon: 5
                                        ,time: 1000
                                    });
                                }
                            }
                            ,error: function(res) {
                                layer.msg(res, {
                                    icon: 5
                                    ,time: 1000
                                });
                            }
                        });
                    })
                })
            }
        })
    }),
    // treeTable.render({
    //     elem: "#LAY-user-back-manage",
    //     url: "/lucky/menu/getMenus?isDelete="+$("#isDelete").val()+"&access_token=" + layui.data(layui.setter.tableName)[layui.setter.request.tokenName],
    //     cols: [[{
    //         type: "checkbox",
    //         fixed: "left"
    //     },
    //     {
    //         field: "id",
    //         width: 80,
    //         title: "ID",
    //         sort: !0
    //     },
    //     {
    //         field: "loginname",
    //         title: "登录名"
    //     },
    //     {
    //         field: "telphone",
    //         title: "手机"
    //     },
    //     {
    //         field: "email",
    //         title: "邮箱"
    //     },
    //     {
    //         field: "role",
    //         title: "角色"
    //     },
    //     {
    //         field: "jointime",
    //         title: "加入时间",
    //         sort: !0
    //     },
    //     {
    //         field: "check",
    //         title: "审核状态",
    //         templet: "#buttonTpl",
    //         minWidth: 80,
    //         align: "center"
    //     },
    //     {
    //         title: "操作",
    //         width: 150,
    //         align: "center",
    //         fixed: "right",
    //         toolbar: "#table-useradmin-admin"
    //     }]],
    //     text: "对不起，加载出现异常！"
    // }),
    // l.on("tool(LAY-user-back-manage)",
    // function(e) {
    //     var l = e.data;
    //     "del" === e.event ? layer.prompt({
    //         formType: 1,
    //         title: "敏感操作，请验证口令"
    //     },
    //     function(i, t) {
    //         layer.close(t),
    //         layer.confirm("确定删除此管理员？",
    //         function(i) {
    //             console.log(e),
    //             e.del(),
    //             layer.close(i)
    //         })
    //     }) : "edit" === e.event && i.popup({
    //         title: "编辑管理员",
    //         area: ["420px", "450px"],
    //         id: "LAY-popup-user-add",
    //         success: function(e, i) {
    //             t(this.id).render("user/administrators/adminform", l).done(function() {
    //                 r.render(null, "layuiadmin-form-admin"),
    //                 r.on("submit(LAY-user-back-submit)",
    //                 function(e) {
    //                     e.field;
    //                     layui.table.reload("LAY-user-back-manage"),
    //                     layer.close(i)
    //                 })
    //             })
    //         }
    //     })
    // }),
    l.render({
        elem: "#LAY-user-back-role",
        url: "/lucky/role/getRoles?access_token=" + layui.data(layui.setter.tableName)[layui.setter.request.tokenName],
        cols: [[{
            type: "checkbox",
            fixed: "left"
        },
        {
            field: "id",
            title: "ID",
            width: 100,
            align: "right"
        },
        {
            field: "roleName",
            title: "角色名称",
            width: 600
        },
        {
            field: "roleDesc",
            title: "角色描述"
        },
        {
            field: "isDelete",
            title: "状态",
            templet: "#isDelete",
            width: 100,
            align: "center"
        },
        {
            title: "操作",
            width: 350,
            align: "center",
            fixed: "right",
            toolbar: "#table-useradmin-admin"
        }]],
        page: !0,
        limit: 50,
        height: "full-280",
        text: { none: '数据不存在！' }
    }),
    l.on("tool(LAY-user-back-role)",
    function(e) {
        var l = e.data;
        "del" === e.event ? layer.confirm("确定删除吗？",
        function(i) {
            layer.close(i);

            var roles = [];
            var trole = {
                id: l.id
            }
            roles.push(trole);

            layui.admin.req({
                url: '/lucky/role/batchDel?access_token=' + layui.data(layui.setter.tableName)[layui.setter.request.tokenName]
                ,type: 'POST'
                ,contentType: 'application/json; charset=utf-8'
                ,data: JSON.stringify({
                    data: roles
                })
                ,success: function(res) {
                    if (res.code === layui.setter.response.statusCode.ok) {
                        //修改成功的提示与跳转
                        layer.msg(res.msg, {
                            offset: '15px'
                            , icon: 1
                            , time: 1000
                        }, function () {
                            table.reload('LAY-user-back-role');
                        });
                    } else if (res.code !== layui.setter.response.statusCode.logout) {
                        layer.msg(res.msg, {
                            icon: 5
                            ,time: 1000
                        });
                    }
                }
                ,error: function(res) {
                    layer.msg(res, {
                        icon: 5
                        ,time: 1000
                    });
                }
            });
        }) : ("edit" === e.event ? i.popup({
            title: "编辑角色",
            area: ["500px", "350px"],
            id: "LAY-popup-role-edit",
            success: function(e, i) {
                t(this.id).render("user/administrators/roleform", l).done(function() {
                    r.render(null, "layuiadmin-form-role"),
                    r.on("submit(LAY-user-role-submit)",
                    function(e) {
                        var field = e.field;
                        field.id = l.id;

                        //提交 Ajax 成功后，关闭当前弹层并重载表格
                        layui.admin.req({
                            url: '/lucky/role/modify?access_token=' + layui.data(layui.setter.tableName)[layui.setter.request.tokenName]
                            ,type: 'POST'
                            ,contentType: 'application/json; charset=utf-8'
                            ,data: JSON.stringify({
                                data: field
                            })
                            ,success: function(res) {
                                if (res.code === layui.setter.response.statusCode.ok) {
                                    layer.close(i); //执行关闭
                                    //修改成功的提示与跳转
                                    layer.msg(res.msg, {
                                        offset: '15px'
                                        , icon: 1
                                        , time: 1000
                                    }, function () {
                                        layui.table.reload('LAY-user-back-role'); //重载表格
                                    });
                                } else if (res.code !== layui.setter.response.statusCode.logout) {
                                    layer.msg(res.msg, {
                                        icon: 5
                                        ,time: 1000
                                    });
                                } else if (res.code === layui.setter.response.statusCode.logout) {
                                    layer.close(i); //执行关闭
                                }
                            }
                            ,error: function(res) {
                                layer.msg(res, {
                                    icon: 5
                                    ,time: 1000
                                });
                            }
                        });
                    })
                })
            }
        }) : ("chk" === e.event ? i.popup({
            title: "查看用户",
            area: ["1500px", "1350px"],
            id: "LAY-popup-role-user",
            success: function(e, i) {
                $("#id").val(l.id);
                t(this.id).render("user/administrators/roleuser", l).done(function() {
                    r.render(null, "layuiadmin-form-roleuser"),
                        r.on("submit(LAY-role-user-submit)",
                            function(e) {
                                layer.close(i); //执行关闭
                            })
                })
            }
        }) : "auth" === e.event && i.popup({
            title: "分配权限",
            area: ["1000px", "1350px"],
            id: "LAY-popup-role-auth",
            success: function(e, i) {
                $("#id").val(l.id);
                $("#ind").val(i);
                t(this.id).render("user/administrators/roleauth", l).done(function() {
                    r.render(null, "layuiadmin-form-roleauth");
                        // r.on("submit(LAY-role-auth-submit)",
                        //     function(e) {
                        //         var field = e.field;
                        //         field.id = l.id;
                        //
                        //         //提交 Ajax 成功后，关闭当前弹层并重载表格
                        //         layui.admin.req({
                        //             url: '/lucky/role/modify?access_token=' + layui.data(layui.setter.tableName)[layui.setter.request.tokenName]
                        //             ,type: 'POST'
                        //             ,contentType: 'application/json; charset=utf-8'
                        //             ,data: JSON.stringify({
                        //                 data: field
                        //             })
                        //             ,success: function(res) {
                        //                 if (res.code === layui.setter.response.statusCode.ok) {
                        //                     layer.close(i); //执行关闭
                        //                     //修改成功的提示与跳转
                        //                     layer.msg(res.msg, {
                        //                         offset: '15px'
                        //                         , icon: 1
                        //                         , time: 1000
                        //                     }, function () {
                        //                         layui.table.reload('LAY-user-back-role'); //重载表格
                        //                     });
                        //                 } else if (res.code !== layui.setter.response.statusCode.logout) {
                        //                     layer.msg(res.msg, {
                        //                         icon: 5
                        //                         ,time: 1000
                        //                     });
                        //                 } else if (res.code === layui.setter.response.statusCode.logout) {
                        //                     layer.close(i); //执行关闭
                        //                 }
                        //             }
                        //             ,error: function(res) {
                        //                 layer.msg(res, {
                        //                     icon: 5
                        //                     ,time: 1000
                        //                 });
                        //             }
                        //         });
                        //     })
                })
            }
        })))
    }),
    e("useradmin", {})
});