/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ 加QQ：1293166442 获取源码版*/
;
layui.define(["form", "upload"],
function(t) {
    var i = layui.$,
    e = layui.layer,
    n = (layui.laytpl, layui.setter, layui.view, layui.admin),
    a = layui.form,
    s = layui.upload;
    i("body");
    a.render(),
    a.verify({
        nickname: function(t, i) {
            return new RegExp("^[a-zA-Z0-9_一-龥\\s·]+$").test(t) ? /(^\_)|(\__)|(\_+$)/.test(t) ? "用户名首尾不能出现下划线'_'": /^\d+\d+\d$/.test(t) ? "用户名不能全为数字": void 0 : "用户名不能有特殊字符"
        },
        pass: [/^[\S]{6,12}$/, "密码必须6到12位，且不能出现空格"],
        repass: function(t) {
            if (t !== i("#LAY_password").val()) return "两次密码输入不一致"
        }
    }),
    a.on("submit(set_website)",
    function(t) {
        return e.msg(JSON.stringify(t.field)),
        !1
    }),
    a.on("submit(set_system_email)",
    function(t) {
        return e.msg(JSON.stringify(t.field)),
        !1
    }),
    a.on("submit(setmyinfo)",
    function(t) {
        //请求接口
        layui.admin.req({
          url: '/lucky/user/chg?access_token=' + layui.data(layui.setter.tableName)[layui.setter.request.tokenName]
          ,type: 'POST'
          ,contentType: 'application/json; charset=utf-8'
          ,data: JSON.stringify({
              data: {
                  id: layui.data(layui.setter.tableName)['id'],
                  username: layui.data(layui.setter.tableName)['username'],
                  nickname: t.field.nickname,
                  sex: i('input[name="sex"]:checked').val(),
                  mobile: t.field.cellphone,
                  email: t.field.email,
                  description: t.field.remarks
              }
          })
          ,success: function(res) {
            if (res.code === layui.setter.response.statusCode.ok) {
              //修改成功的提示与跳转
              layer.msg(res.msg, {
                offset: '15px'
                , icon: 1
                , time: 1000
              }, function () {
                location.hash = '/';
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
    });
    var r = i("#LAY_avatarSrc");
    s.render({
        url: "/api/upload/",
        elem: "#LAY_avatarUpload",
        done: function(t) {
            0 == t.status ? r.val(t.url) : e.msg(t.msg, {
                icon: 5
            })
        }
    }),
    n.events.avartatPreview = function(t) {
        var i = r.val();
        e.photos({
            photos: {
                title: "查看头像",
                data: [{
                    src: i
                }]
            },
            shade: .01,
            closeBtn: 1,
            anim: 5
        })
    },
    a.on("submit(setmypass)",
    function(t) {
        //请求接口
        layui.admin.req({
          url: '/lucky/user/chgPwd?access_token=' + layui.data(layui.setter.tableName)[layui.setter.request.tokenName]
          ,type: 'POST'
          ,contentType: 'application/json; charset=utf-8'
          ,data: JSON.stringify({
              data: {
                  id: layui.data(layui.setter.tableName)['id'],
                  username: layui.data(layui.setter.tableName)['username'],
                  password: t.field.password,
                  oldPassword: t.field.oldPassword
              }
          })
          ,success: function(res) {
            if (res.code === layui.setter.response.statusCode.ok) {
              //修改成功的提示与跳转
              layer.msg(res.msg, {
                offset: '15px'
                , icon: 1
                , time: 1000
              }, function () {
                location.hash = '/';
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
    }),
    t("set", {})
});