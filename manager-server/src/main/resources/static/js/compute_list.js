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
                hardware:{
                    hostName:"iZrj9fpaid84lev5yy85m1Z",
                    platform:"Ubuntu",
                    version:"16.04",
                    cpu_Core:"1",
                    totalMemory:"4",
                    diskAll:"3",
                },
                score:"47.254.69.78",
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
                score:"47.88.52.90",
                user:"NNU_Group"
            },{
                hardware:{
                    hostName:"launch-advisor-20191209",
                    platform:"Windows Server",
                    version:"2012 R2",
                    cpu_Core:"2",
                    totalMemory:"4",
                    diskAll:"3",
                },
                score:"47.74.66.87",
                user:"Sirius"
            },{
                hardware:{
                    hostName:"WIN-VH4GMM75DJH",
                    platform:"Windows Server",
                    version:"2012 R2",
                    cpu_Core:"4",
                    totalMemory:"8",
                    diskAll:"20",
                },
                score:"172.21.213.105",
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
                score:"172.21.212.85",
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
                score:"172.25.111.173",
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
                score:"172.21.213.66",
                user:"wangming"
            },{
                hardware:{
                    hostName:"iZm5e81qbhfk423he1vxovZ",
                    platform:"CentOS",
                    version:"7.6",
                    cpu_Core:"2",
                    totalMemory:"8",
                    diskAll:"5",
                },
                score:"118.190.246.198",
                user:"NNU_Group"
            },{
                hardware:{
                    hostName:"ming",
                    platform:"CentOS",
                    version:"7.3",
                    cpu_Core:"1",
                    totalMemory:"2",
                    diskAll:"1",
                },
                score:"47.107.155.239",
                user:"wangming"
            },{
                hardware:{
                    hostName:"shencr-CMIP",
                    platform:"Ubuntu",
                    version:"16.04",
                    cpu_Core:"4",
                    totalMemory:"4",
                    diskAll:"20",
                },
                score:"172.21.212.58",
                user:"Sirius"
            },{
                hardware:{
                    hostName:"LAPTOP-0PNLCFNL",
                    platform:"Windows",
                    version:"10",
                    cpu_Core:"4",
                    totalMemory:"8",
                    diskAll:"4",
                },
                score:"223.2.35.64",
                user:"wangming"
            }]
        };
    },
    methods: {
        handleSelect(key, keyPath) {
            console.log(key, keyPath);
        },
        handlePageChange() {

        }
    },
    mounted() {

    }
})