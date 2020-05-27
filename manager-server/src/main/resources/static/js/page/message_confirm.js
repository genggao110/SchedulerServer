var notice = Vue.extend({
    template:"#notice",
    components: {
        'avatar': VueAvatar.Avatar
    },
    data:function () {
        return {

            ScreenMinHeight: "0px",
            ScreenMaxHeight: "0px",
            curIndex:3,
            dialogVisible1: false,
            dialogVisible2: false,
            dialogVisible3: false,


            currentDate: new Date(),
            activeName: 'first',
            model:[{
                past:{
                    category_name:"",
                    model:[{
                        modelname:""
                    }]
                },
                edited:{
                    category_name:"",
                    model:[{
                        modelname:""
                    }]
                }
            }],
            data:[{
                past:{
                    category_name:"",
                    model:[{
                        modelname:""
                    }]
                },
                edited:{
                    category_name:"",
                    model:[{
                        modelname:""
                    }]
                }
            }],
            application:[{
                name:"",
                link:"",
                image:""
            }],
            user_information:{
                Name:"",
                Email:""
            },
            useroid:"",
            username:"",
            model_tableData1:[],
            model_tableData2: [],
            model_tableData3:[],
            model_tableData1_length:0,
            model_tableData2_length:0,
            model_tableData3_length:0,

            edit_model_tableData:[],//用于获取model的version数据，用于显示谁编辑了什么
            community_tableData1:[],
            community_tableData2:[],
            community_tableData3:[],
            community_tableData1_length:0,
            community_tableData2_length:0,
            community_tableData3_length:0,
            edit_community_tableData:[],//用于获取community的version数据，用于显示谁编辑了什么

            theme_tableData1:[],
            theme_tableData2:[],
            theme_tableData3:[],
            theme_tableData1_length:0,
            theme_tableData2_length:0,
            theme_tableData3_length:0,


            table_length_sum:0,
            sum_tableData:[],//为了解决时间线多个v-for无法将多个表格数据时间正序排列的问题，将所有表格数据放到一个表格中
            //存放Info临时点击数据
            info_past_dialog:"",
            info_edited_dialog:"",
            //存放model临时点击数据
            classinfo:[],
            dataClassInfo:[],
            application:[],

            sub_classinfo:[],
            sub_dataClassInfos:[],
            sub_applications:[],

            // //控制点击theme view的时候显示的是哪个
            info_seen:false,
            model_seen:false,
            data_seen:false,
            application_seen: false,

            message_num:0,

            reverse: true,

            comments:[],

            loading: true,

            invitesAccept:[],

            invitesUnapprv:[],
        };
    },
    methods:{
        handleClick(tab, event){
            console.log(tab, event);
        },
        handleClose(done) {
            this.$confirm('Confirm closing？')
                .then(_ => {
                    done();
                })
                .catch(_ => {});
        },
        sendcurIndexToParent(){
            this.$emit('com-sendcurindex',this.curIndex)
        },
        handleClick(row) {
            console.log(row);
        },

        // getEditVersion(){
        //     $.ajax({
        //         type:"GET",
        //         url:"/version/getEditVersion",
        //         data:{},
        //         async:false,
        //         success:(json)=>{
        //             console.log(json);
        //         }
        //     })
        // },

        bbSort(list,keyWord){
            //将sum_tableData的数据按照时间排序(冒泡排序)
            for (let i=0;i<list.length;i++){
                for (let j=list.length-1;j>i;j--){
                    if (list[j][keyWord]>list[j-1][keyWord]){
                        let temp = list[j];
                        list[j] = list[j-1];
                        list[j-1] = temp;
                    }
                }
            }
            return list
        },

        getColor(item){
            if(item.status == 0)
                return '#20d1d4'
            else
                return '#cf9318'
        },

        getInvites(){
            $.ajax({
                type: "GET",
                url: "/user/getInvite",
                data: {},
                cache: false,
                async: false,
                xhrFields: {
                    withCredentials: true
                },
                crossDomain: true,
                success:(res)=>{
                    if(res.code == -1){
                        alert("Please login");
                        window.location.href = "/user/login";
                    }else{
                        let data = res.data;
                        this.invites = data;
                        this.bbSort(this.invites,'createDate')

                        this.invitesAccept = this.invites.filter((ele)=>{
                            return ele.status==1
                        })
                        this.invitesUnapprv = this.invites.filter((ele)=>{
                            return ele.status==0
                        })

                    }
                }
            })
        },

        accept(invite){
            $.ajax({
                type:'POST',
                url:'/user/acceptInvite',
                data:{
                    invite:invite.oid,
                },
                async: true,
                success:(res)=>{
                    if(res.code == -1){
                        alert("Please login");
                        window.location.href = "/user/login";
                    }else{
                        this.alert('Accet invite successful!','success',{
                                confirmButtonText: 'OK',
                            }
                            )
                    }
                }
            })
        },
    },
    mounted(){
        this.sendcurIndexToParent();

        this.getInvites();
        $(() => {

            let height = document.documentElement.clientHeight;
            this.ScreenMinHeight = (height) + "px";
            this.ScreenMaxHeight = (height) + "px";

            window.onresize = () => {
                console.log('come on ..');
                height = document.documentElement.clientHeight;
                this.ScreenMinHeight = (height) + "px";
                this.ScreenMaxHeight = (height) + "px";
            };
        });
        let that = this;
    }
})