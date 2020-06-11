new Vue({
    el: '#app',
    components: {
        'avatar': VueAvatar.Avatar
    },
    data: function () {
        return {
            activeIndex2: '1-3',
            list: [{
                "image": "",
                "keywords": ["System: Windows_NT", "CPU: 4 Core", "Disk: 20.01G"],
                "createTime": "2019-05-06T12:00:24.604+0000",
                "author": "NNU_Group",
                "name": "WIN-VR3MDCFJ70H",
                "description": "",
                "id": "5cd021d86af45610b4b19572",
                "viewCount": 169
            }, {
                "image": "",
                "keywords": ["System: Windows_NT", "CPU: 8 Core", "Disk: 72.01G"],
                "createTime": "2019-05-07T15:24:32.490+0000",
                "author": "NNU_Group",
                "name": "DESKTOP-62UC2I5",
                "description": "",
                "id": "5cd1a3306af45609247f6ecb",
                "viewCount": 51
            }, {
                "image": "",
                "keywords": ["System: Linux", "CPU: 8 Core", "Disk: 35.01G"],
                "createTime": "2019-05-07T15:27:50.341+0000",
                "author": "NNU_Group",
                "name": "localdomain",
                "description": "","id": "5cd1a3f66af45609247f6ecc",
                "viewCount": 4
            }, {
                "image": "",
                "keywords": ["System: Windows_NT", "CPU: 4 Core", "Disk: 256.01G"],
                "createTime": "2019-05-07T15:30:27.191+0000",
                "author": "NNU_Group",
                "name": "WIN-JAMH103AR5Q",
                "description": "","id": "5cd1a4936af45609247f6ecd",
                "viewCount": 5
            }, {
                "image": "",
                "keywords": ["System: Windows_NT", "CPU: 4 Core", "Disk: 60.01G"],
                "createTime": "2019-05-07T15:33:16.607+0000",
                "author": "NNU_Group",
                "name": "WIN-9BBVMC1FNP5",
                "description": "","id": "5cd1a53c6af45609247f6ece",
                "viewCount": 2
            }, {
                "image": "",
                "keywords": ["System: Windows_NT", "CPU: 8 Core", "Disk: 47.01G"],
                "createTime": "2019-05-07T15:35:58.694+0000",
                "author": "NNU_Group",
                "name": "WIN-UDTFI3N5V09",
                "description": "","id": "5cd1a5de6af45609247f6ecf",
                "viewCount": 1
            }, {
                "image": "",
                "keywords": ["System: Windows_NT", "CPU: 4 Core", "Disk: 121.01G"],
                "createTime": "2019-05-07T15:38:51.446+0000",
                "author": "NNU_Group",
                "name": "Franklin-PC",
                "description": "","id": "5cd1a68b6af45609247f6ed0",
                "viewCount": 1
            }, {
                "image": "",
                "keywords": ["System: Windows_NT", "CPU: 2 Core", "Disk: 12.01G"],
                "createTime": "2019-05-07T15:41:08.883+0000",
                "author": "NNU_Group",
                "name": "WIN-9BBVMC1FNP5",
                "description": "","id": "5cd1a7146af45609247f6ed1",
                "viewCount": 2
            }, {
                "image": "",
                "keywords": ["System: Windows_NT", "CPU: 4 Core", "Disk: 68.01G"],
                "createTime": "2019-05-09T13:47:45.321+0000",
                "author": "NNU_Group",
                "name": "WIN-9068R3NAREN",
                "description": "","id": "5cd42f816af4560a78eff7fb",
                "viewCount": 2
            }],

            pageOption: {
                users: [{"image": "", "name": "NNU_Group", "oid": "42"}, {
                    "image": "",
                    "name": "NNU_Group",
                    "oid": "42"
                }, {"image": "", "name": "NNU_Group", "oid": "42"}, {
                    "image": "",
                    "name": "NNU_Group",
                    "oid": "42"
                }, {"image": "", "name": "NNU_Group", "oid": "42"}, {
                    "image": "",
                    "name": "NNU_Group",
                    "oid": "42"
                }, {"image": "", "name": "NNU_Group", "oid": "42"}, {
                    "image": "",
                    "name": "NNU_Group",
                    "oid": "42"
                }, {"image": "", "name": "NNU_Group", "oid": "42"}, {"image": "", "name": "NNU_Group", "oid": "42"}],
                paginationShow: false,
                progressBar: true,
                sortAsc: false,
                currentPage: 1,
                pageSize: 10,

                total: 264,
                searchResult: [],
            },
            modelContainerList:[{
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
                hostName:"ming",
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
            modelContainerListShow:[],
        };
    },
    methods: {
        handleSelect(key, keyPath) {
            console.log(key, keyPath);
        },
        handlePageChange() {

        },
        getComputerForDeploy(){
            let data = this.runtimeInfo;
            this.loadRecmd = true
            this.activeTab='tab1'

            $.ajax({
                url: "/computer/all",
                async: true,
                type: "GET",
                contentType: "application/json",
                data: JSON.stringify(data),
                success: (result) => {
                    console.log(result);
                    if(result.code != -1){

                        this.modelContainerList =this.modelContainerList.concat(result.data) ;


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

    },
    mounted() {
        this.getComputerForDeploy();
    }
})