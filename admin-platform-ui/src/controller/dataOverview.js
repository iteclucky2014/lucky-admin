/**

 @Name：layuiAdmin 内容系统
 @Author：star1029
 @Site：http://www.layui.com/admin/
 @License：LPPL
    
 */


layui.define(['table', 'form', 'laydate', 'treeSelect', 'admin'], function (exports) {
  var $ = layui.$
    , admin = layui.admin
    , view = layui.view
    , table = layui.table
    , form = layui.form
    , laydate = layui.laydate
    , treeSelect = layui.treeSelect
    , element = layui.element;

  var localData = layui.data(layui.setter.tableName);

  // 所属分区
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
      this.where = {};
      layer.msg("success");
      layer.msg(d.current.name);
      table.reload('test-table-reload1', {
        url: './json/dataOverview/flowmeter.js' //模拟接口
        , page: { curr: 1 }
        , where: { area: d.current.name }
      });
    },
    // console.log($('#deptId').val());
    // 加载完成后的回调函数
    // 选中节点，根据id筛选
    //treeSelect.checkNode('deptTree', $('#deptId').val());
  });

  //流量计数据表
  table.render({
    elem: '#test-table-reload1'
    , url: './json/dataOverview/flowmeter.js' //模拟接口
    , height: '380px'
    , cellMinWidth: 80
    , page: true
    , toolbar: '#test-table-toolbar-toolbarDemo1'
    , cols: [[
      { field: 'number', title: '编号', width: 100, sort: true, align: "left" }
      , { field: 'time', title: '时间', sort: true, align: 'left' }
      , { field: 'area', title: '隶属分区', sort: true, align: 'left' }
      , { field: 'caliber', title: '口径', sort: true, align: 'left' }
      , { field: 'totalFlow', title: '累计流量', align: 'left' }
      , { field: 'actualFlow', title: '实用流量', sort: true, align: 'left' }
    ]]
    , page: true
    , limit: 5
    , limits: [5, 10, 20, 30]
    , text: '对不起，加载出现异常！'
  });

  //压力计数据表
  table.render({
    elem: '#test-table-reload2'
    , url: './json/dataOverview/manometer.js' //模拟接口
    , height: '380px'
    , cellMinWidth: 80
    , page: true
    , toolbar: '#test-table-toolbar-toolbarDemo2'
    , cols: [[
      { field: 'number', title: '编号', width: 100, sort: true, align: "left" }
      , { field: 'time', title: '时间', sort: true, align: 'left' }
      , { field: 'area', title: '隶属分区', sort: true, align: 'left' }
      , { field: 'pressure', title: '压力值', sort: true, align: 'left' }
    ]]
    , page: true
    , limit: 5
    , limits: [5, 10, 20, 30]
    , text: '对不起，加载出现异常！'
  });


  exports('dataOverview', {})
});