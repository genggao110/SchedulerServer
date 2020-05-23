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
            tableData1: [{
                date: '2016-05-02',
                name: 'NNU_Group',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                date: '2016-05-04',
                name: 'wangming',
                address: '上海市普陀区金沙江路 1517 弄'
            }, {
                date: '2016-05-01',
                name: 'Sirius',
                address: '上海市普陀区金沙江路 1519 弄'
            }],
            tableData2: [{
                date: '2016-05-02',
                name: 'SWAT_Model',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                date: '2016-05-04',
                name: 'TaiHu_Fvcom',
                address: '上海市普陀区金沙江路 1517 弄'
            }, {
                date: '2016-05-01',
                name: 'FVCOM',
                address: '上海市普陀区金沙江路 1519 弄'
            }, {
                date: '2016-05-03',
                name: 'BDS',
                address: '上海市普陀区金沙江路 1516 弄'
            }, {
                date: '2016-05-03',
                name: 'DCBAH',
                address: '上海市普陀区金沙江路 1516 弄'
            }, {
                date: '2016-05-03',
                name: 'SWMM',
                address: '上海市普陀区金沙江路 1516 弄'
            }, {
                date: '2016-05-03',
                name: 'SEIMS',
                address: '上海市普陀区金沙江路 1516 弄'
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
            }]
        };
    },
    methods: {
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
            $.ajax({
                url: "/modelResource/getComputerForDeploy",
                async: true,
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(data),
                success: (result) => {
                    console.log(result);
                    if(result.code == 0){
                        console.log(result.data);
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
                agentId:"5eb937b92578b13a60196191",
                mac:"00:50:56:c0:00:01",
                type:"2",
                userName:"wangming",
                modelName:"SWATModelAllInOne",
                md5:"b7db289c3fab296b266711be0b220ce4",
                oid:"57158339-57a1-4dbb-820e-bf2677a0df3c"
            }
            let url = '/modelResource/deployModel';
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
        }


    },
    mounted() {
        //1. 从服务器端根据计算模型id获取模型信息(/modelResource/getInfo/57158339-57a1-4dbb-820e-bf2677a0df3c)
        //2. 获取到该项目可用的计算资源信息(目前咱们就利用全部的计算资源做实验，后期改良)
        var id = "57158339-57a1-4dbb-820e-bf2677a0df3c";
        this.getModelResourceInfo(id);
        //目前这里是获取到所有的可用计算资源
        this.getAllAvailableComputerResource();
    }
})