/**

 @Name：layuiAdmin 内容系统
 @Author：star1029
 @Site：http://www.layui.com/admin/
 @License：LPPL
    
 */


layui.define(['table', 'form', 'laydate', 'treeSelect'], function (exports) {
  var $ = layui.$
    , admin = layui.admin
    , view = layui.view
    , table = layui.table
    , treeSelect = layui.treeSelect
    , form = layui.form
    , laydate = layui.laydate;

  var localData = layui.data(layui.setter.tableName);

  treeSelect.render({
    // 选择器
    elem: '#deptName',
    // 数据
    data: '/platform/dept/getDeptTreeData?access_token=' + localData["access_token"],
    // 异步加载方式：get/post，默认get
    type: 'get',
    // 占位符
    placeholder: '修改默认提示信息',
    // 是否开启搜索功能：true/false，默认false
    search: true,
    // 点击回调
    click: function (d) {
      console.log(d.current.id);
      $('#belongRegion').val(d.current.id);
    },
    // 加载完成后的回调函数
    success: function (d) {
      console.log(d);
      // 选中节点，根据id筛选
    }
  });

  form.render(null, 'app-content-list');

  //监听搜索
  form.on('submit(LAY-app-contlist-search)', function (data) {
    var field = data.field;
    // 整理复选框数据
    var platformDeviceSubmitParams = [];
    //执行重载
    table.reload('LAY-app-content-list', {
      where: field
    });
    
    /* ajax使用例
    $.ajax({
      url: '/platform/device/search'
      , type: 'POST'
      , contentType: 'application/json; charset=utf-8'
      , data: JSON.stringify({
        data: field
      })
      , dataType: 'json'
      , success: function (resp) {
        if (resp.code === 0) {

          });
        } else {
          layer.msg(检索失败);
        }
      }
      , error: function (resp) {
        layer.msg(resp.msg, { icon: 6 });
      }
    }); */

  });

  //设备管理
  table.render({
    elem: '#LAY-app-content-list'
    /* , url: './json/assetManagement/region.js' //模拟接口 */
    , url: '/platform/device/page?access_token=' + localData["access_token"]
    , cellMinWidth: 80
    , toolbar: '#test-table-toolbar-toolbarDemo'
    , cols: [[
      { type: 'checkbox', fixed: 'left' }
      , { field: 'code', title: '编号', width: 100, sort: true }
      , { field: 'standard', title: '规格', width: 200, sort: true }
      , { field: 'manuFacturer', title: '生产厂家', width: 100, sort: true }
      , { field: 'installDate', title: '安装日期', width: 200, sort: true }
      , { field: 'installLocation', title: '安装位置', width: 200 }
      , { field: 'alarmRules', title: '报警规则', sort: true }
      , { field: 'belongRegion', title: '隶属分区', sort: true }
      , { title: '操作', minWidth: 150, align: 'center', fixed: 'right', toolbar: '#table-content-list' }
    ]]
    , page: true
    , limit: 10
    , limits: [10, 15, 20, 25, 30]
    , text: { none: '一条数据也没有^_^' }
  });

  //头工具栏事件
  table.on('toolbar(LAY-app-content-list)', function (obj) {
    var checkStatus = table.checkStatus('LAY-app-content-list')
      , checkData = checkStatus.data; //得到选中的数据
    switch (obj.event) {
      case 'batchdel':
        if (checkData.length === 0) {
          return layer.msg('请选择数据');
        }
        layer.confirm('确定删除吗？', function (index) {

          //执行 Ajax 后重载
          /*
          admin.req({
            url: 'bill'
            //,……
          });
          */
          table.reload('LAY-app-content-list');
          layer.msg('已删除');
        });
        break;
      case 'add':
        var data = obj.data;
        admin.popup({
          title: '添加文章'
          , area: ['600px', '750px']
          , id: 'LAY-popup-content-add'
          , success: function (layero, index) {
            view(this.id).render('assetManagement/regionform').done(function () {

              //常规用法
              laydate.render({
                elem: '#testlaydatenormalcn'
              });
              form.render(null, 'layuiadmin-app-form-list');

              //监听提交
              form.on('submit(layuiadmin-app-form-submit)', function (data) {
                var field = data.field; //获取提交的字段

                //提交 Ajax 成功后，关闭当前弹层并重载表格
                //$.ajax({});
                layui.table.reload('LAY-app-content-list'); //重载表格
                layer.close(index); //执行关闭 
              });
            });
          }
        });
        break;
    };
  });

  //监听工具条
  table.on('tool(LAY-app-content-list)', function (obj) {
    var data = obj.data;
    if (obj.event === 'del') {
      layer.confirm('确定删除此分区？', function (index) {
        obj.del();
        layer.close(index);
      });
    } else if (obj.event === 'edit') {

      admin.popup({
        title: '编辑'
        , area: ['600px', '750px']
        , id: 'LAY-popup-content-edit'
        , success: function (layero, index) {
          view(this.id).render('assetManagement/regionform', data).done(function () {
            //常规用法
            laydate.render({
              elem: '#testlaydatenormalcn'
            });
            form.render(null, 'layuiadmin-app-form-list');

            //监听提交
            form.on('submit(layuiadmin-app-form-submit)', function (data) {
              var field = data.field; //获取提交的字段

              //提交 Ajax 成功后，关闭当前弹层并重载表格
              //$.ajax({});
              layui.table.reload('LAY-app-content-list'); //重载表格
              layer.close(index); //执行关闭 
            });
          });
        }
      });
    }
  });
  exports('region', {})
});