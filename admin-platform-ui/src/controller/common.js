/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ 加QQ：1293166442 获取源码版*/
;
layui.define(function(e) {
    var i = (layui.$, layui.layer, layui.laytpl, layui.setter, layui.view, layui.admin);
    i.events.logout = function() {
        i.req({
            url: "/lucky/logout",
            type: "POST",
            data: {},
            success: function(e) {
                i.exit()
            }
        })
    },
    e("common", {})
});