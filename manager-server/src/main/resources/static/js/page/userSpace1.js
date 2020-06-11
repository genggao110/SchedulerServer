ELEMENT.locale(ELEMENT.lang.en)

//此页面为根文件，控制路由切换
//侧边栏选中高亮由路由关键字判断，在sidebar中
var router = new VueRouter({
        routes:[
            // {
            //     path:'/',
            //     redirect:'/home',
            // },
            {
                path:'/',
                component:userProjects,
            },

            {
                path:'/project',
                component:userProjects,
            },
            {
                path:'/resource',
                component:userResource,
            },
            {
                path:'/message',
                component:notice,

            },

            //
            // {
            //     path:'/logicalmodel',
            //     component:modelItem,
            // },


        ]
    });
var vue = new Vue(
    {
        el: "#app",
        data(){
            return{

                message_num:0,
                tableData: [{
                    info:[],
                    model:[],
                    data:[],
                    application:[]
                }],

                useroid:"",
                //页面样式控制
                loading: 'false',
                load: true,
                ScreenMinHeight: "0px",
                ScreenMaxHeight: "0px",

                //显示控制
                curIndex:1,
                itemIndex:1,//父组件的控制变量

                //
                userInfo:{

                },

                //websocket
                websktPath:"ws://localhost:8080/websocket",
                userspaceSocket:"",

            }
        },

        router:router,

        methods:{
            // websocket
            initWebSkt:function () {

                if ('WebSocket' in window) {
                    // this.userspaceSocket = new WebSocket("ws://localhost:8080/websocket");
                    this.userspaceSocket = new WebSocket(this.websktPath)
                    // 监听socket连接
                    this.userspaceSocket.onopen = this.open
                    // 监听socket错误信息
                    this.userspaceSocket.onerror = this.error
                    // 监听socket消息
                    this.userspaceSocket.onmessage = this.getMessage

                }
                else {
                    // alert('当前浏览器 Not support websocket');
                    console.log("websocket 无法连接");
                }
            },

            open: function () {
                console.log("父组件socket连接成功")
            },
            error: function () {
                console.log("连接错误");
            },
            getMessage: function (msg) {
                if(msg.data === 'user change')
                    this.getUserInfo();
            },
            //

            //公共功能
            setSession(name, value) {
                window.sessionStorage.setItem(name, value);
                // this.editOid = sessionStorage.getItem('editItemOid');
            },

            // creatItem(index){
            //     window.sessionStorage.removeItem('editOid');
            //     if(index == 1) window.location.href='../user/userSpace/model/createModelItem'
            // },

            // 修改index值，改变显示
            changecurIndex(index){
                if(index != null&&index != undefined){
                    this.curIndex = index
                }
            },

            changeitemIndex(index){
                if(index != null&&index != undefined){
                    this.itemIndex = index
                }
            },

            getUserInfo() {
                axios.get('/user/load').then(
                    res => {
                        if(res.data.code==0){
                            this.userInfo = res.data.data

                            this.load = false;
                        }

                    }
                )
            },

            updateUserInfo(userId){
                if(this.userInfo.oid!=userId)
                    this.getUserInfo();

            },

        },

        destroyed () {
            // 销毁监听
            this.userspaceSocket.onclose = this.close
        },

        created() {
        },

        mounted() {
            let that= this;
            that.initWebSkt();//初始化websocket

            //用于消息判断
            $(document).on('click','.share-button',function ($event) {
                $.ajax({
                    url: "/theme/getoid",
                    async: false,
                    success:(data)=>{
                        that.useroid = data;
                    }
                })
                window.location.href = "/theme/getmessagepage/" + that.useroid;
            })

            $(() => {
                // let height = document.documentElement.clientHeight;
                // this.ScreenMinHeight = (height) + "px";
                // this.ScreenMaxHeight = (height) + "px";
                //
                // window.onresize = () => {
                //     console.log('come on ..');
                //     height = document.documentElement.clientHeight;
                //     this.ScreenMinHeight = (height) + "px";
                //     this.ScreenMaxHeight = (height) + "px";
                // };


                $.ajax({
                    type: "GET",
                    url: "/user/load",
                    data: {},
                    cache: false,
                    async: false,
                    xhrFields: {
                        withCredentials: true
                    },
                    crossDomain: true,
                    success: (data) => {
                        data = JSON.parse(data);

                        // console.log(data);

                        if (data.oid == "") {
                            alert("Please login");
                            window.location.href = "/user/login";
                        } else {
                            this.userId = data.oid;
                            this.userName = data.name;
                            console.log(this.userId)
                            // this.addAllData()

                            // axios.get("/dataItem/amountofuserdata",{
                            //     params:{
                            //         userOid:this.userId
                            //     }
                            // }).then(res=>{
                            //     that.dcount=res.data
                            // });

                            $("#author").val(this.userName);

                            var index = window.sessionStorage.getItem("index");
                            if (index != null && index != undefined && index != "" && index != NaN) {
                                this.defaultActive = index;
                                this.handleSelect(index, null);
                                window.sessionStorage.removeItem("index");
                                this.curIndex=index

                            } else {
                                // this.changeRter(1);
                            }

                            window.sessionStorage.removeItem("tap");
                            //this.getTasksInfo();
                            this.load = false;
                        }
                    }
                })


                //this.getModels();
            });
        },

    }
);

