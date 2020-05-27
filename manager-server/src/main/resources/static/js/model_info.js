new Vue({
    el: '#app',
    components: {
        'avatar': VueAvatar.Avatar
    },
    data: function () {

        return {
            activeIndex2: '1-1',
            computer_oid: '',
            tableData1: [{
                oid: '42',
                name: 'NNU_Group',
                email:'',
                userName:'njgis',
            },  ],
            userAll: [{
                oid: '1',
                name: '',
                email:'',
                userName:'',
            }, {
                oid: '2',
                name: '',
                email:'',
                userName:'',
            }, ],
            chosenUser:[

            ],
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
            modelResourceInfo:{

            },
            resources:{
                "id": 0,
                "name": '',
                "suffix": '',
                "path": ''
            },
            user:{"image": "", "name": "NNU_Group", "oid": "42"},
            tableData:{
                name: '',
                version: '',
                baseDir: '',
                entry: '',
                platform: '',
                hardware: [],
                software: [],
                assemblies: [],
                validate: []
            },
            oldtableData: {
                name: '',
                version: '',
                baseDir: '',
                entry: '',
                hardware: [],
                software: [],
                assemblies: [],
                support: []
            },
            oldtableStatus: false,
            modelContainerList:[{
                hardware:{
                    hostName:"WIN-VR3MDCFJ70H",
                    platform:"Windows",
                    version:"10.0.18362",
                    cpu_Core:"8",
                    totalMemory:"16",
                    diskAll:"520",
                },
                score:"86",
            },{
                hardware:{
                    hostName:"WIN-VR3MDCFJ70H",
                    platform:"Windows",
                    version:"10.0.18362",
                    cpu_Core:"8",
                    totalMemory:"16",
                    diskAll:"520",
                },
                score:"86",
            },{
                hardware:{
                    hostName:"WIN-VR3MDCFJ70H",
                    platform:"Windows",
                    version:"10.0.18362",
                    cpu_Core:"8",
                    totalMemory:"16",
                    diskAll:"520",
                },
                score:"86",
            }],

            modelContainerList1:[{
                hardware:{
                    hostName:"DESKTOP-3K2K270",
                    platform:"Windows",
                    version:"10",
                    cpu_Core:"12",
                    totalMemory:"24",
                    diskAll:"4",
                },
                ip:"172.25.111.173",
                score:"96",
                user:"NNU_Group"
            },{
                hardware:{
                    hostName:"WIN-VH4GMM75DJH",
                    platform:"Windows Server",
                    version:"2012 R2",
                    cpu_Core:"4",
                    totalMemory:"8",
                    diskAll:"20",
                },
                ip:"172.21.213.105",
                score:"85",
                user:"NNU_Group"
            },{
                hardware:{
                    hostName:"WIN-DEABAKGA1HS",
                    platform:"Windows Server",
                    version:"2012 R2",
                    cpu_Core:"8",
                    totalMemory:"4",
                    diskAll:"20",
                },
                ip:"172.21.212.85",
                score:"83",
                user:"NNU_Group"
            },{
                hardware:{
                    hostName:"LAPTOP-0PNLCFNL",
                    platform:"Windows",
                    version:"10",
                    cpu_Core:"4",
                    totalMemory:"8",
                    diskAll:"4",
                },
                ip:"223.2.35.64",
                score:"80",
                user:"wangming"
            },{
                hardware:{
                    hostName:"launch-advisor-20191209",
                    platform:"Windows Server",
                    version:"2012 R2",
                    cpu_Core:"2",
                    totalMemory:"4",
                    diskAll:"3",
                },
                ip:"47.74.66.87",
                score:"74",
                user:"Sirius"
            }],
            modelContainerList:[{
                hardware:{
                    hostName:"iZrj9fpaid84lev5yy85m1Z",
                    platform:"Ubuntu",
                    version:"16.04",
                    cpu_Core:"1",
                    totalMemory:"4",
                    diskAll:"3",
                },
                ip:"47.254.69.78",
                score:"86",
                user:"NNU_Group"
            },{
                hardware:{
                    hostName:"iZrj9fpaid84lev5yy85m2Z",
                    platform:"Ubuntu",
                    version:"16.04",
                    cpu_Core:"2",
                    totalMemory:"4",
                    diskAll:"3",
                },
                ip:"47.88.52.90",
                score:"86",
                user:"NNU_Group"
            },{
                hardware:{
                    hostName:"WIN-VH4GMM75DJH",
                    platform:"Windows Server",
                    version:"2012 R2",
                    cpu_Core:"4",
                    totalMemory:"8",
                    diskAll:"20",
                },
                ip:"172.21.213.105",
                score:"86",
                user:"NNU_Group"
            },{
                hardware:{
                    hostName:"WIN-DEABAKGA1HS",
                    platform:"Windows Server",
                    version:"2012 R2",
                    cpu_Core:"8",
                    totalMemory:"4",
                    diskAll:"20",
                },
                ip:"172.21.212.85",
                score:"86",
                user:"NNU_Group"
            },{
                hardware:{
                    hostName:"DESKTOP-3K2K270",
                    platform:"Windows",
                    version:"10",
                    cpu_Core:"12",
                    totalMemory:"24",
                    diskAll:"4",
                },
                ip:"172.25.111.173",
                score:"86",
                user:"NNU_Group"
            },{
                hardware:{
                    hostName:"iZm5e81qbhfk423he1vxovZ",
                    platform:"CentOS",
                    version:"7.6",
                    cpu_Core:"2",
                    totalMemory:"8",
                    diskAll:"5",
                },
                ip:"118.190.246.198",
                score:"86",
                user:"NNU_Group"
            },{
                hardware:{
                    hostName:"localdomain",
                    platform:"CentOS",
                    version:"7.7",
                    cpu_Core:"2",
                    totalMemory:"12",
                    diskAll:"20",
                },
                ip:"172.21.213.66",
                score:"86",
                user:"wangming"
            },{
                hardware:{
                    hostName:"LAPTOP-0PNLCFNL",
                    platform:"Windows",
                    version:"10",
                    cpu_Core:"4",
                    totalMemory:"8",
                    diskAll:"4",
                },
                ip:"223.2.35.64",
                score:"86",
                user:"wangming"
            },{
                hardware:{
                    hostName:"ming",
                    platform:"CentOS",
                    version:"7.3",
                    cpu_Core:"1",
                    totalMemory:"2",
                    diskAll:"1",
                },
                ip:"47.107.155.239",
                score:"86",
                user:"wangming"
            },{
                hardware:{
                    hostName:"launch-advisor-20191209",
                    platform:"Windows Server",
                    version:"2012 R2",
                    cpu_Core:"2",
                    totalMemory:"4",
                    diskAll:"3",
                },
                ip:"47.74.66.87",
                score:"86",
                user:"Sirius"
            },{
                hardware:{
                    hostName:"shencr-CMIP",
                    platform:"Ubuntu",
                    version:"16.04",
                    cpu_Core:"4",
                    totalMemory:"4",
                    diskAll:"20",
                },
                ip:"172.21.212.58",
                score:"86",
                user:"Sirius"
            }],
            userChooseDialog:false,
            deployDialog:false,
            loadingUser:{},
            modelContainerUse:[],
            modelContainerListInviting:[],
        };
    },
    computed: {
        //计算属性
        createTime: function () {
            let create_time = this.modelResourceInfo.createTime;
            return create_time == null ? '2018-7-13': create_time.substring(0,10);
        },
        modelDetail: function () {
            // let detail = this.modelResourceInfo.detail;
            // let result = detail.replace(/\//g,'');
            // console.log(result);
            // return result;
            let test = "<p>testtetsttfasf</p>>";
            return test;
        }
    },
    methods: {
        handleSelect(key, keyPath) {
            console.log(key, keyPath);
        },
        handlePageChange() {

        },

        softwareHandle(software){
            let value = software.value + ' | ' + software.platform  + ' | ';
            let version = '';
            if(software.minVersion != 'null'){
                version += '[' + software.minVersion;
                if(software.maxVersion != 'null'){
                    version += ', ' + software.maxVersion + ']';
                }else {
                    version += ', infinite]';
                }
            }else {
                if(software.maxVersion != 'null'){
                    version += '[infinite, '+ software.maxVersion + ']';
                }else{
                    version += '[infinite, infinite]';
                }
            }
            return  (value + version);
        },

        oldSoftwareHandle(software){
            let value = software.value + ' | ' + software.platform;
            return value;
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
                            this.tableData1.push(this.loadingUser);
                        }
                    }

            })
        },

        getModelResourceInfo() {
            let url = '/modelResource/getInfo/' + this.computer_oid;
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
                        this.modelResourceInfo = result;
                        this.resources = result.resources;
                        let runtime_json = JSON.parse(result.runtime);
                        //判断哪个是新老版本
                        if(runtime_json.platform == null){
                            this.oldtableData = runtime_json;
                            this.oldtableStatus = true;
                        }else {
                            this.tableData = runtime_json;
                        }
                        console.log(runtime_json);
                    }else{
                        this.$message({
                            message: '获取模型资源详细描述信息失败',
                            type: 'error'
                        });
                    }
                }
            })
        },

        deployModel(){

            let hrefs = window.location.href.split('/')
            window.sessionStorage.setItem('oid',hrefs[hrefs.length-1])
            window.location.href='/modelResource/createProject'
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

                            this.modelContainerUse = this.modelContainerList.filter((ele)=>{
                                return idList.indexOf(ele.mac)==-1
                            })
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

        openDeployDialog(){
            this.deployDialog = true;
            let invited = [],inviting = [];
            this.tableData1.forEach((ele)=>{
                invited.push(ele.userName)
            })
            this.chosenUser.forEach((ele)=>{
                inviting.push(ele.userName)
            })
            this.modelContainerUse =  this.getAvailableComputerResourceByUser(invited)
        },

        showAllUser(){
            this.userChooseDialog = true;
            $.ajax({
                type: "GET",
                url:'/portal/user/getUsers',
                success:(res)=>{
                    if(res.code==0){
                        this.userAll = res.data;
                    }else{
                        this.$alert('Failed to get users, please try again', 'warning', {
                            confirmButtonText: 'OK',
                            callback: action => {

                            }
                        });
                    }
                }
            })
        },

        inviteUser(user){
            if(this.chosenUser.indexOf(user)!=-1||user.oid=='42'||user.oid==this.loadingUser.oid)
                this.$alert('This user is invited, select another one.',  {
                    confirmButtonText: 'OK',
                    callback: action => {

                    }
                });
            else {
                this.chosenUser.push(user)
                let inviting = []
                inviting.push(user.userName)
                let a = this.getAvailableComputerResourceByUser(inviting)
                if(Object.keys(a).length != 0){
                    this.modelContainerListInviting.push(a[0])
                }
            }

        },

        cancelInvite(user){
            this.chosenUser = this.chosenUser.filter(
                (ele)=>{
                    return ele!=user;
                }
            )

            this.modelContainerListInviting=this.modelContainerListInviting.filter((ele)=>{
                    return ele.userId!=user.userName
                }
            )
        },

        creatDeployProject(){
            let hrefs = window.location.href.split('/')
            // let invited =[],inviting = []
            // this.tableData1.forEach((ele)=>{
            //     invited.push(ele.userName)
            // })
            // this.chosenUser.forEach((ele)=>{
            //     inviting.push(ele.userName)
            // })
            let obj={
                owner:this.loadingUser.userName,
                invitedUsers:this.tableData1,
                invitingUsers:this.chosenUser,
                model:hrefs[hrefs.length-1],
                modelName:this.modelResourceInfo.name,
            }
            $.ajax({
                url: "/project/create",
                async: true,
                type: "POST",
                contentType: 'application/json',
                data: JSON.stringify(obj),

                success:(res)=>{
                    if(res=="-1"){
                        this.alert('Please log in first', 'warning', {
                            confirmButtonText: 'OK',
                            callback: action => {

                            }
                        });
                        window.location.href='/user/login';
                    }else if(res=='0'){
                        this.$alert('Failed to create project, please try again',  {
                            confirmButtonText: 'OK',
                            callback: action => {

                            }
                        });
                    }else{
                        this.$confirm('<div style=\'font-size: 18px\'>Create project successfully!</div>', 'Tip', {
                            dangerouslyUseHTMLString: true,
                            confirmButtonText: 'View',
                            cancelButtonText: 'Go Back',
                            cancelButtonClass: 'fontsize-15',
                            confirmButtonClass: 'fontsize-15',
                            type: 'success',
                            center: true,
                            showClose: false,
                        }).then(() => {
                            window.location.href = "/project/" + res;
                        }).catch(() => {
                            // window.location.href = window.location.href;
                        });
                        this.deployDialog = false
                        this.selectedModel = {
                            name:'',
                            createTime:'',
                        }
                        this.projectTitle = ''
                        this.projectDecription = ''
                        this.chosenUser = []
                        this.modelContainerListInviting = []

                    }
                }
            })
        }
    },
    mounted() {
        //themleaf模板中将oid赋值给了全局window对象
        this.computer_oid = window.oid;
        this.getModelResourceInfo();
        this.loadUser()
    }
})