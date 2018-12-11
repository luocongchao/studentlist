<template>
    <div class="layout_container">
        <div class="layout_header">
            <el-button class="table_add_col" raw-type="button" icon="el-icon-d-caret" size="small" :loading="btnloding" @click="addTabCol">添加列</el-button>
            <el-button raw-type="button" icon="el-icon-d-caret" size="small" @click="addTabRow">添加行</el-button>
            <el-button raw-type="button" icon="el-icon-check" size="small" @click="addTabRow">保存</el-button>
            <el-button raw-type="button" icon="el-icon-download" size="small" @click="exportExcel">导入Excel</el-button>
            <el-button raw-type="button" icon="el-icon-upload2" size="small" @click="exportExcel">导出Excel</el-button>
        </div>
        <div id="table_oneTable" class="layout_body">
            <el-table :data="tableData" border style="width: 100%" :height="tabH">
                <el-table-column v-for="(col,index) in cols" :key="index" min-width="200" :render-header="renderHeader" :label="col.label">
                    <template slot-scope="scope">{{scope.row[col.key]}}</template>
                </el-table-column>
                <el-table-column v-if="cols.length>0" fixed="right" min-width="100" label="操作">
                    <template slot-scope="scope">
                        <el-button type="text" size="small" @click="editRow(scope.row,scope.$index)">编辑</el-button>
                        <el-button type="text" size="small" @click="removeRow(scope.row,scope.$index)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="layout_boot"></div>

        <div>
            <el-dialog title="编辑信息" :height="tabH" :visible.sync="dialogFormVisible" v-on:close="editModel=[]">
                <el-form label-width="120px">
                    <el-form-item v-for="(item,index) in editModel" :key="index" :label="item.label" class="table_form_label" size="small">
                        <el-input autocomplete="off" v-model="item.value"></el-input>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="saveRow">确 定</el-button>
                </div>
            </el-dialog>
        </div>
    </div>
</template>
<script>
import excel from "@/api/excel";
import { mapGetters } from "vuex";

export default {
    computed: {
        ...mapGetters(["name"])
    },
    data() {
        return {
            dialogFormVisible: false,
            formLabelWidth: 120,
            tabH: null,
            tableData: [],
            cols: [],
            editModel: [],
            cureditmodel: null,
            number: 0,
            titlekey: "标题",
            btnloding: false
        };
    },
    methods: {
        saveRow() {
            this.editModel.forEach(item => {
                this.cureditmodel[item.key] = item.value;
            });
            this.dialogFormVisible = !this.dialogFormVisible;
            this.cureditmodel = null;
        },
        editRow(row, index) {
            if (this.cols.length == 0) return;
            this.cols.forEach(item => {
                var obj = {};
                obj = {
                    key: item.key,
                    label: item.label,
                    value: row[item.key]
                };
                this.editModel.push(obj);
            });
            this.cureditmodel = row;
            this.dialogFormVisible = !this.dialogFormVisible;
        },

        removeRow(row, index) {
            this.tableData.splice(index, 1);
        },
        removeCol(index) {
            var key = this.cols[index].key;
            this.cols.splice(index, 1);
            this.tableData.forEach(item => {
                delete item[key];
            });
            console.log(this.tableData);
            console.log(this.cols);
        },

        //渲染表头
        renderHeader(h, { column, $index }) {
            return h("span", {}, [
                h("span", {}, column.label),
                h(
                    "i",
                    {
                        slot: "reference",
                        class: "el-icon-close layou_error_icon",
                        on: {
                            click: () => {
                                this.removeCol($index);
                            }
                        }
                    },
                    ""
                )
            ]);
        },
        //添加列数据
        addTabCol() {
            this.btnloding = true;
            this.$prompt("请输入列标题名称", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                inputValue: this.titlekey + (this.number + 1)
            })
                .then(({ value }) => {
                    var _this = this;
                    var addname = _this.titlekey + ++_this.number;
                    _this.tableData.forEach(item => {
                        while (item.hasOwnProperty(addname)) {
                            addname = _this.titlekey + ++_this.number;
                        }
                        item[addname] = "";
                    });
                    this.cols.push({
                        key: addname,
                        label: value
                    });
                    this.btnloding = false;
                })
                .catch(() => {
                    this.btnloding = false;
                });
        },
        //添加列数据
        addTabRow() {
            if (this.cols.length == 0) {
                this.$notify({
                    title: "警告",
                    message: "请先添加列和标题",
                    type: "warning"
                });
                return;
            } else {
                var obj = {};

                this.cols.forEach(item => {
                    obj[item.key] = "";
                });
                this.tableData.push(obj);
            }
        },
        //导出Excel表格
        exportExcel() {
            if (this.cols.length) {
                //this.exportLoading = true;
                var title = [];
                this.cols.forEach(item => {
                    title.push(item.label);
                });
                var key = [];
                this.cols.forEach(item => {
                    key.push(item.key);
                });
                const params = {
                    title: title,
                    key: key,
                    data: this.tableData,
                    autoWidth: true,
                    filename: this.name + "分类列表"
                };
                excel.export_array_to_excel(params);
                //this.exportLoading = false;
            } else {
                this.$notify.info({
                    title: "消息",
                    message: "表格数据不能为空！"
                });
            }
        }
    },
    mounted: function() {
        var height = document.getElementById("table_oneTable").offsetHeight;
        this.tabH = height;
    }
};
</script>

 



