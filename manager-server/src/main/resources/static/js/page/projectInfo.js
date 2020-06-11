new Vue({
    el: '#app',
    components: {
        'avatar': VueAvatar.Avatar
    },
    data: function () {

        return {
            activeIndex2: '2',
            modelItem:{
                "oid": "3f6857ba-c2d2-4e27-b220-6e5367803a12",
                "name": "SWAT_Model",
                "image": "",
                "description": "The Soil & Water Assessment Tool is a small watershed to river basin-scale model used to simulate the quality and quantity of surface and ground water and predict the environmental impact of land use, land management practices, and climate change. SWAT is widely used in assessing soil erosion prevention and control, non-point source pollution control and regional management in watersheds.",
                "author": "NNU_Group",
                "status": "public",
                "keywords": ["SWAT", "Hydrology"],
                "createTime": "2018-06-13T06:19:16.079+0000",
                "viewCount": 1688
            },
            runtimeInfo: {

            },
            computerResource: [],

            user:{"image": "", "name": "NNU_Group", "oid": "42"},
            invitedUsers: [{
                oid: '42',
                name: 'NNU_Group',
                email:'',
                userName:'njgis',
            },  ],
            modelContainerListPre:[{
                hostName:"iZrj9fpaid84lev5yy85m1Z",
                software_info:{
                    os:"Ubuntu",
                    version:"16.04",
                },
                hardware_info:{
                    staticInfo: {
                        platform: "Ubuntu",
                        version: "16.04",
                        cpu_core: "1",
                        memory_size: "4",
                        disk_all: "3",
                    }
                },
                ip:"47.254.69.78",
                score:"86",
                user:"NNU_Group"
            },{
                hostName:"iZrj9fpaid84lev5yy85m2Z",
                software_info:{
                    os:"Ubuntu",
                    version:"16.04",
                },
                hardware_info:{
                    staticInfo: {
                        platform: "Ubuntu",
                        version: "16.04",
                        cpu_core: "2",
                        memory_size: "4",
                        disk_all: "3",
                    }
                },
                ip:"47.88.52.90",
                score:"86",
                user:"NNU_Group"
            },{
                hostName:"launch-advisor-20191209",
                software_info:{
                    os:"Windows Server",
                    version:"2012 R2",
                },
                hardware_info:{
                    staticInfo: {
                        platform: "Windows Server",
                        version: "2012 R2",
                        cpu_core: "2",
                        memory_size: "4",
                        disk_all: "3",
                    }
                },
                ip:"47.74.66.87",
                score:"86",
                user:"NNU_Group"
            },{
                hostName:"iZm5e81qbhfk423he1vxovZ",
                software_info:{
                    os:"CentOS",
                    version:"7.6",
                },
                hardware_info:{
                    staticInfo: {
                        platform: "CentOS",
                        version: "7.6",
                        cpu_core: "2",
                        memory_size: "8",
                        disk_all: "5",
                    }
                },
                ip:"118.190.246.198",
                score:"86",
                user:"NNU_Group"
            },{
                hostName:"iZm6t75qbhfk156xj2mjlvP",
                software_info:{
                    os:"CentOS",
                    version:"7.3",
                },
                hardware_info:{
                    staticInfo: {
                        platform: "CentOS",
                        version: "7.3",
                        cpu_core: "1",
                        memory_size: "2",
                        disk_all: "1",
                    }
                },
                ip:"47.107.155.239",
                score:"86",
                user:"NNU_Group"
            },{
                hostName:"WIN-VH4GMM75DJH",
                software_info:{
                    os:"Windows Server",
                    version:"2012 R2",
                },
                hardware_info:{
                    staticInfo: {
                        platform: "Windows Server",
                        version: "2012 R2",
                        cpu_core: "4",
                        memory_sizeemory: "8",
                        disk_all: "20",
                    }
                },
                ip:"172.21.213.105",
                score:"86",
                user:"NNU_Group"
            },{
                hostName:"WIN-DEABAKGA1HS",
                software_info:{
                    os:"Windows Server",
                    version:"2012 R2",
                },
                hardware_info:{
                    staticInfo: {
                        platform: "Windows Server",
                        version: "2012 R2",
                        cpu_core: "8",
                        memory_size: "4",
                        disk_all: "20",
                    }
                },
                ip:"172.21.212.85",
                score:"86",
                user:"NNU_Group"
            },{
                hostName:"DESKTOP-3K2K270",
                software_info:{
                    os:"Windows",
                    version:"10",
                },
                hardware_info:{
                    staticInfo: {
                        platform: "Windows",
                        version: "10",
                        cpu_core: "12",
                        memory_size: "24",
                        disk_all: "4",
                    }
                },
                ip:"172.25.111.173",
                score:"86",
                user:"NNU_Group"
            },{
                hostName:"localdomain",
                software_info:{
                    os:"CentOS",
                    version:"7.7",
                },
                hardware_info: {
                    staticInfo: {
                        platform: "CentOS",
                        version: "7.7",
                        cpu_core: "2",
                        memory_size: "12",
                        disk_all: "20",
                    },
                },
                ip:"172.21.213.66",
                score:"86",
                user:"NNU_Group"
            },{
                hostName:"shencr-CMIP",
                software_info:{
                    os:"Ubuntu",
                    version:"16.04",
                },
                hardware_info:{
                    staticInfo: {
                        platform: "Ubuntu",
                        version: "16.04",
                        cpu_core: "4",
                        memory_size: "4",
                        disk_all: "20",
                    }
                },
                ip:"172.21.212.58",
                score:"86",
                user:"NNU_Group"
            },{
                hostName:"LAPTOP-0PNLCFNL",
                software_info:{
                    os:"Windows",
                    version:"10",
                },
                hardware_info:{
                    staticInfo: {
                        platform: "Windows",
                        version: "10",
                        cpu_core: "4",
                        memory_size: "8",
                        disk_all: "4",
                    }
                },
                ip:"223.2.35.64",
                score:"86",
                user:"NNU_Group"
            }],
            invitingUsers:[

            ],
            modelContainerList:[
              ],
            modelContainerListInviting:[],
            modelContainerUse:[],
            recommend:[],
            activeTab:'tab1',
            loadRecmd:false,
            chosenResource:{},
            loadingUser:{},
            projectInfo:{},
        };
    },
    methods: {
        formatDate(value, callback) {
            const date = new Date(value);
            y = date.getFullYear();
            M = date.getMonth() + 1;
            d = date.getDate();
            H = date.getHours();
            m = date.getMinutes();
            s = date.getSeconds();
            if (M < 10) {
                M = '0' + M;
            }
            if (d < 10) {
                d = '0' + d;
            }
            if (H < 10) {
                H = '0' + H;
            }
            if (m < 10) {
                m = '0' + m;
            }
            if (s < 10) {
                s = '0' + s;
            }

            const t = y + '-' + M + '-' + d + ' ' + H + ':' + m + ':' + s;
            if (callback == null || callback == undefined)
                return t;
            else
                callback(t);
        },

        handleSelect(key, keyPath) {
            console.log(key, keyPath);
        },
        handlePageChange() {

        },
        open() {
            this.$alert('Deployed successfully!', 'Notice', {
                confirmButtonText: 'OK',
                callback: action => {
                    this.$message({
                        type: 'info',
                        message: `action: ${ action }`
                    });
                }
            })
        },

        //将模型的rutime信息转换为
        handlerRuntimeInfo(runtime) {
            var runtime_json = JSON.parse(runtime);
            let runtime_object = {};
            runtime_object.platform = runtime_json.platform;
            runtime_object.hardware = runtime_json.hardware;
            runtime_object.software = runtime_json.software;
            return runtime_object;
        },

        getComputerForDeploy(){
            let data = this.runtimeInfo;
            this.loadRecmd = true
            this.activeTab='tab1'

            $.ajax({
                url: "/modelResource/getComputerForDeploy",
                async: true,
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(data),
                success: (result) => {
                    console.log(result);
                    if(result.code == 0){

                        setTimeout(()=>{
                            console.log(result.data);
                            this.recommend = result.data;

                            let idList = this.recommend.map(ele => ele.mac);

                            this.recommend = this.modelContainerList.filter((ele)=>{
                                return idList.indexOf(ele.mac)!=-1
                            })

                            this.recommend[0].score=94
                            this.recommend[1].score=89
                            this.recommend[2].score=86

                            this.modelContainerUse = this.modelContainerList.filter((ele)=>{
                                return idList.indexOf(ele.mac)==-1
                            })
                            this.modelContainerUse = this.modelContainerListPre.concat(this.modelContainerUse)
                            this.loadRecmd =false
                        },350)

                    }else {
                        this.$message({
                            message: 'get computerResouce error!',
                            type: 'error',
                            offset: 40,
                            showClose: true,
                        });
                    }
                }
            })
        },

        deployModel(){
            //example input json
            let data = {
                packagePath:"http://127.0.0.1:8080/static/computableModel/Package/wangming/1589983016420_SWATModelAllInOne.zip",
                agentId:this.chosenResource.agentId,
                mac:this.chosenResource.mac,
                type:"2",
                userName:this.chosenResource.userId,
                modelName:this.modelItem.name,
                md5:this.modelItem.md5,
                oid:this.modelItem.oid,
                projectId:this.projectInfo.oid,
            }
            let url = '/modelResource/deployModel';


            $.ajax({
                    url: url,
                    async: true,
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    success: (result) => {
                        console.log("deploy"+result.data)
                        setTimeout(()=>{
                            this.$alert('Deploy model successfully',{
                                confirmButtonText: 'OK',
                                callback: action => {

                                }
                            })
                        },500)
                    }
             })
            //TODO
        },


        getModelResourceInfo(id){
            let url = '/modelResource/getInfo/' + id;
            $.ajax({
                type: "GET",
                url: url,
                data: {},
                async: false,
                cache: false,
                xhrFields: {
                    withCredentials: true
                },
                crossDomain: true,
                success: (data) => {
                    if(data.code == 0){
                        let result = data.data.info;
                        console.log(result);
                        this.modelItem = result
                        this.runtimeInfo = this.handlerRuntimeInfo(result.runtime);
                        console.log(this.runtimeInfo);
                    }else{
                        this.$message({
                            message: '获取模型资源详细描述信息失败',
                            type: 'error'
                        });
                    }
                }
            })
        },

        getAllAvailableComputerResource(){
            let url = "/computer/all";
            $.ajax({
                type: "GET",
                url: url,
                data: {},
                async: false,
                cache: false,
                xhrFields: {
                    withCredentials: true
                },
                crossDomain: true,
                success: (data) => {
                    if(data.code == 0){
                        let result = data.data;
                        console.log(result);
                        this.computerResource = result;
                    }else{
                        this.$message({
                            message: '获取计算资源信息失败',
                            type: 'error'
                        });
                    }
                }
            })
        },

        getAvailableComputerResourceByUser(userNames){
            let url = "/computer/getByUsers";
            let result = []
            $.ajax({
                type: "GET",
                url: url,
                data: {userNames:userNames},
                async: false,
                cache: false,
                xhrFields: {
                    withCredentials: true
                },
                crossDomain: true,
                success: (data) => {
                    if(data.code == 0){
                        result = data.data;
                        console.log(result);
                    }else{
                        this.$message({
                            message: '获取计算资源信息失败',
                            type: 'error'
                        });
                    }
                }
            })
            return result
        },

        getProject(){
            let hrefs = window.location.href.split('/')
            let id = hrefs[hrefs.length-1]

            $.ajax({
                type: "GET",
                url: '/project/getById',
                data: {id:id},
                async: false,
                cache: false,
                xhrFields: {
                    withCredentials: true
                },
                crossDomain: true,
                success: (data) => {
                    if(data.code != -1){
                        let result = data.data;
                        console.log(result);
                        this.projectInfo = result;
                        this.invitedUsers = result.invitedUsers;
                        this.invitingUsers = result.invitingUsers;
                        this.getModelResourceInfo(result.model)
                        let invited = [],inviting = [];
                        this.invitedUsers.forEach((ele)=>{
                            invited.push(ele.userName)
                        })
                        this.invitingUsers.forEach((ele)=>{
                            inviting.push(ele.userName)
                        })
                        this.modelContainerList =  this.getAvailableComputerResourceByUser(invited)
                        this.modelContainerUse =  this.getAvailableComputerResourceByUser(invited)
                        this.modelContainerUse = this.modelContainerListPre.concat(this.modelContainerUse)
                        this.modelContainerListInviting =  this.getAvailableComputerResourceByUser(inviting)
                    }else{
                        // this.$message({
                        //     message: '获取计算资源信息失败',
                        //     type: 'error'
                        // });
                    }
                }
            })
        },

        loadUser(){
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
                success: (res) => {
                    res = JSON.parse(res)
                    if (res.oid == "") {
                        alert("Please login");
                        window.location.href = "/user/login";
                    } else {
                        this.loadingUser=res
                    }
                }

            })
        },

        chooseResource($event,resource,index){

            if(this.recommend.length>0)
                index=index+3
            $(".serverCard").removeClass('chosen')
            $(".cirqueBoxContainer").css('display','none')
            $(".cirqueBoxContainer").eq(index).css('display','block')
            setTimeout(()=>{  $(".checkResource").eq(index).css('display','block')},1450)

            $event.currentTarget.className = "el-card box-card serverCard is-always-shadow chosen"
            this.chosenResource = resource
        },

        chooseRecommend($event,resource,index){

            $(".serverCard").removeClass('chosen')
            $(".cirqueBoxContainer").css('display','none')
            $(".cirqueBoxContainer").eq(index).css('display','block')
            setTimeout(()=>{  $(".checkResource").eq(index).css('display','block')},1450)

            $event.currentTarget.className = "el-card box-card serverCard is-always-shadow chosen"
            this.chosenResource = resource
        },

        cancelChoose(){
            $(".serverCard").removeClass('chosen')
            $(".cirqueBoxContainer").css('display','none')

            this.chosenResource = {}
        }
    }


    ,
    mounted() {
        //1. 从服务器端根据计算模型id获取模型信息(/modelResource/getInfo/57158339-57a1-4dbb-820e-bf2677a0df3c)
        //2. 获取到该项目可用的计算资源信息(目前咱们就利用全部的计算资源做实验，后期改良)
        // var id = "57158339-57a1-4dbb-820e-bf2677a0df3c";
        // this.getModelResourceInfo(id);
        //目前这里是获取到所有的可用计算资源
        this.getAllAvailableComputerResource();

        this.getProject();

        this.loadUser();


    }
})