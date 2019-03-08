layui.define(["form"], function(exports) {
	var MOD_NAME = "treetable",
		$ = layui.jquery,
		form = layui.form,
		tree = function() {};
	tree.prototype.cinfig = function(e) {
		this.c = $.extend({
			elem: "#tree-table",
			field: "id",
			icon_class: "down",
			icon_val: {
				open: "&#xe623;",
				close: "&#xe625;"
			},
			space: 4,
			new_data: [],
			childs: [],
			is_open: false,
		}, e)
	};
	tree.prototype.on = function(events, callback) {
		return layui.onevent.call(this, MOD_NAME, events, callback)
	};
	tree.prototype.template = function(data) {
		var t = this,
			level = [],
			tbody = "",
			thead = t.c.is_checkbox ? '<td><input type="checkbox" lay-skin="primary" lay-filter="lay-t"></td>' : '';
		$.each(t.c.cols, function(idx, obj) {
			thead += '<th style="width:' + obj.width + '">' + obj.title + "</th>"
		});
		$.each(data, function(index, item) {
			var hide_class = 'class="' + (item.pid == 0 || item.pid == t.cache(item.pid) || t.c.is_open ? "" : "hide") + '"',
				tr = '<tr data-id="' + item.id + '" data-pid="' + item.pid + '" ' + hide_class + ">" +
				(t.c.is_checkbox ? '<td><div><input type="checkbox" lay-skin="primary" lay-filter="lay-t"></div></td>' : "");
			level[item.id] = item.pid > 0 ? (level[item.pid] + 1) : 0;
			$.each(t.c.cols, function(idx, obj) {
				tr += '<td style="width:' + obj.width + '">';
				if(obj.field == t.c.field) {
					tr += ("&nbsp;".repeat(level[item.id] * t.c.space));
					if(t.c.childs[item.id]) {
						tr += '<i class="layui-icon ' + t.c.icon_class + '">' + (item.id == t.cache(item.id) || t.c.is_open ? t.c.icon_val.close : t.c.icon_val.open) + "</i>"
					}
				}
				tr += (item[obj.field] !== undefined ? item[obj.field] : (obj.template ? obj.template(item) : "")) + "</td>"
			});
			tbody += tr + "</tr>";
		});
		return '<thead><tr data-id="0" data-pid="-1">' + thead + "</tr></thead><tbody>" + tbody + "</tbody>"
	};
	tree.prototype.render = function(e) {
		var t = this,
			data = [];
		t.cinfig(e);
		$.each(t.c.data, function(index, item) {
			if(!t.c.childs[item.pid]) {
				t.c.childs[item.pid] = []
			}
			t.c.childs[item.pid][item.id] = t.c.new_data[item.id] = data[item.id] = item
		});
		var tree = this.tree(data, 0, [], 0),
			template = t.template(tree);
		$(t.c.elem).html(template).on("click", "td", function() {
			var id = $(this).parents("tr").data("id"),
				pid = $(this).parents("tr").data("pid"),
				status = $(t.c.elem).find("tr[data-pid=" + id + "]").is(":visible"),
				dt = $(this).find("." + t.c.icon_class);
			if(dt.length) {
				if(status) {
					t.hide(id);
					dt.html(t.c.icon_val.open)
				} else {
					$(t.c.elem).find("tr[data-pid=" + id + "]").removeClass('hide');
					t.cache(id, true);
                    t.show(id);
					dt.html(t.c.icon_val.close)
				}
			}
			var filter = $(this).parents("[lay-filter]").attr("lay-filter");
			return filter ? layui.event.call(this, MOD_NAME, MOD_NAME + "(" + filter + ")", {
				elem: $(this),
				status: status,
				item: t.c.new_data[id],
				childs: t.c.childs[id],
				siblings: t.c.childs[pid],
				index: $(this).index(),
				is_last: $(this).index() + 1 == $(this).parents("tr").find("td").length,
			}) : ""
		}).on("click", "td [lay-filter]", function() {
			var id = $(this).parents("tr").data("id"),
				filter = $(this).attr("lay-filter");
			return layui.event.call(this, MOD_NAME, MOD_NAME + "(" + filter + ")", {
				elem: $(this),
				item: t.c.new_data[id],
			})
		})
		form.render('checkbox').on('checkbox(lay-t)', function(data) {
			var status = $(data.othis).hasClass('layui-form-checked'),
				tr = $(data.elem).parents('tr');
			t.child_to_choose(tr.data('id'), status);
			t.parent_to_choose(tr.data('pid'));
			form.render('checkbox');
		})
	};
	tree.prototype.parent_to_choose = function(id) {
		var t = this,
			pt = $(t.c.elem).find('[data-pid=' + id + ']'),
			pl = pt.find('[lay-skin=primary]:checked').length,
			bt = $(t.c.elem).find('[data-id=' + id + '] [lay-skin=primary]'),
			pid = $(t.c.elem).find('[data-id=' + id + ']').data('pid');
		if(pt.length == pl || pl == 0) {
			bt.prop('checked', pt.length == pl);
			pid > -1 && t.parent_to_choose(pid);
		}
	};
	tree.prototype.child_to_choose = function(id, status) {
		var t = this;
		$(t.c.elem).find("tr[data-pid=" + id + "]").each(function() {
			$(this).find('[lay-skin=primary]').prop('checked', status);
			var id = $(this).data("id");
			t.child_to_choose(id, status)
		});
	};
	tree.prototype.hide = function(id) {
		var t = this;
		$(t.c.elem).find("tr[data-pid=" + id + "]").each(function() {
			$(this).addClass('hide');
			$(this).find("." + t.c.icon_class).html(t.c.icon_val.open);
			var id = $(this).data("id");
			t.hide(id)
			$(this).hide();
		});
		t.cache(id, false)
	};
	tree.prototype.show = function(id) {
		var t = this;
		$(t.c.elem).find("tr[data-pid=" + id + "]").each(function() {
			$(this).removeClass('hide');
			$(this).find("." + t.c.icon_class).html(t.c.icon_val.close);
			var id = $(this).data("id");
			t.show(id)
            $(this).show();
		});
		t.cache(id, true)
	};
	tree.prototype.tree = function(lists, pid, data) {
		var t = this;
		if(lists[pid]) {
			data.push(lists[pid]);
			delete lists[pid]
		}
		$.each(t.c.data, function(index, item) {
			if(item.pid == pid) {
				data.concat(t.tree(lists, item.id, data))
			}
		});
		return data
	};
	tree.prototype.cache = function(val, option) {
		var t = this,
			name = "tree-table-open-item",
			val = val.toString(),
			cache = t.get_cookie(name) ? t.get_cookie(name).split(",") : [],
			index = $.inArray(val, cache);
		if(option === undefined) {
			return index == -1 ? false : val
		}
		if(option && index == -1) {
			cache.push(val)
		}
		if(!option && index > -1) {
			cache.splice(index, 1)
		}
		t.set_cookie(name, cache.join(","))
	};
	tree.prototype.set_cookie = function(name, value, days) {
		var exp = new Date();
		exp.setTime(exp.getTime() + (days ? days : 30) * 24 * 60 * 60 * 1000);
		document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString()
	};
	tree.prototype.get_cookie = function(name) {
		var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
		if(arr = document.cookie.match(reg)) {
			return unescape(arr[2])
		} else {
			return null
		}
	};
	tree.prototype.all = function(type) {
		var t = this;
		if(type == "up") {
			$(t.c.elem).find("tr[data-pid=0]").each(function() {
				var id = $(this).data("id");
				t.hide(id);
				$(this).find("." + t.c.icon_class).html(t.c.icon_val.open)
			})
		} else if(type == "down") {
			$(t.c.elem).find("tr[data-pid=0]").each(function() {
				var id = $(this).data("id");
				t.show(id);
				$(this).find("." + t.c.icon_class).html(t.c.icon_val.close)
			})
		} else if(type == "checked") {
			var ids = [],
				data = [];
			$(t.c.elem).find("tbody [lay-skin=primary]:checked").each(function() {
				var id = $(this).parents('tr').data("id");
				data.push(t.c.new_data[id]);
				ids.push(id);
			})
			return {
				ids: ids,
				data: data
			};
		}
	};
	var tree = new tree();
	exports(MOD_NAME, tree)
});