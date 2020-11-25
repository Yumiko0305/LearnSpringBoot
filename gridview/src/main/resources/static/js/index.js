$(function(){

    $("#dg").datagrid({
        url:'/user/findAll',//查询所有用户信息  json  rows total
        fit:true,
        method:"Get",
        pageSize:10,
        pageNumber:1,
        pageList:[10,20,50],
        pagination:true,
        fitColumns:true,
        remoteSort:false,
        toolbar:'#tb',
        columns:[[
            {field:'cks',checkbox:true},
            {title:'ID',field:'id',width:100,},
            {title:'姓名',field:'name',width:100,resizable:false},
            {title:'年龄',field:'age',width:150,sortable:true},
            {title:'创建日期',field:'bir',width:200,sortable:true,order:'asc'},
            {title:'操作',field:'options',width:100,
                formatter:function(value,row,index){
                    return "<a href='javascript:;' class='btn' onClick=\"delUserInfo('"+ row.id +"');\"  data-options=\"iconCls:'icon-remove'\" >删除</a>&nbsp;&nbsp;"
                        +"<a data-options=\"iconCls:'icon-edit'\" onClick=\"openUpdateDialog('"+ row.id +"');\" href='javascript:;' class='btn'>修改</a>";
                }
            },
        ]],
        onLoadSuccess:function(){
            $(".btn").linkbutton({plain:true});
        }
    });





});

//用来处理搜索的函数
function qq(value,name){
    //console.log(value);
    console.log(name);
    $("#dg").datagrid('load',{
        "coloumName":name,
        "coloumValue":value
    })
}

//用来处理对话框
function openSaveDialog(){
    //打开对话框
    $('#da').dialog({
        width:600,
        height:400,
        title:'添加用户信息',
        iconCls:'icon-man',
        href:'templates/saveUserInfo.html',
        buttons:[{
            text:'保存信息',
            iconCls:'icon-save',
            handler:saveUserInfo,
        },{
            text:'取消',
            iconCls:'icon-cancel',
            handler:function (){
                $('#da').dialog('close');
            }
        }]
    });
}
//保存用户信息
function saveUserInfo(){
    $('#saveUserForm').form({
        url:'/user/save',
        onSubmit:function (){
            return $('#saveUserForm').form('validate');
        },
        success:function (data){
            var jsonObj = $.parseJSON(data);
            if(jsonObj.ok==true){
                //map json
                $.messager.show({
                    title:'保存提示',
                    msg:'保存用户信息成功',
                    timeout:5000,
                    shoeType:'slide'
                });
                //关闭对话框
                $('#da').dialog('close');
                $("#dg").datagrid('reload');
            }else{
                $.messager.alert('提示','添加失败，请重新添加','info');
                $('#da').dialog('close');
            }

        }
    });
    //提交form
    $('#saveUserForm').form('submit');
}

//删除用户信息
function delUserInfo(id){
    console.log(id);
    $.messager.confirm('提示','确认要删除这条数据吗？',function (r){
        if(r){
            //删除数据
            $.post("/user/delete",{"id":id},function (result){
                //console.log(typeof result);
                $("#dg").datagrid('reload');
            });
        }
    });

}

//打开修改对话框
function openUpdateDialog(id){
    $("#updateDialog").dialog({
        title:'用户修改信息',
        iconCls:'icon-man',
        width:600,
        height:400,
        href:'templates/updateUserInfo.html?id='+id,
        buttons:[{
            text:'保存信息',
            iconCls:'icon-save',
            handler:updateUserInfo,
        },{
            text:'取消',
            iconCls:'icon-cancel',
            handler:function (){
                $('#updateDialog').dialog('close');
            }
        }]
    });
}

//处理用户修改信息
function updateUserInfo(){
    var id=$("#userId").val();
    //form表单提交
    $("#updateUserForm").form({
       url:'/user/update?id='+id,
       onSubmit:function (){
           return $(this).form('validate');
       } ,
        success:function (data){
           console.log(data);
            var jsonObj = $.parseJSON(data);
            if(jsonObj.success==true){
                $.messager.show({
                    title:'保存提示',
                    msg:'修改用户信息成功',
                    timeout:5000,
                    shoeType:'slide'
                });
                $("#dg").datagrid('reload');
                $('#updateDialog').dialog('close');
            }else{
                $.messager.alert('提示','修改失败，请重新修改','info');
                $('#updateDialog').dialog('close');
            }
            $("#dg").datagrid('reload');
        }
    });
    $('#updateUserForm').form('submit');
}

//处理删除选中
function delSelectRows(){
    var rows = $('#dg').datagrid('getChecked');
    if(rows.length>0){
        var ids = [];
        $.each(rows,function (idx,row){
           ids.push(row.id);
        });
        $.messager.confirm('提示','确认要删除这条数据吗？',function (r){
            if(r){
                //异步请求删除数据
                $.ajax({
                    url:"user/deleteAll",
                    method:"POST",
                    data:{id:ids},
                    dataType:"JSON",
                    traditional:true,//传递数据类型的参数
                    success:function (data){
                        $("#dg").datagrid('reload');
                    }
                });
            }
        });

    }else{
        $.messager.alert('提示','至少勾选一个要删除的数据！','info')
    }
}