new Vue({
    el: '#app',
    components: {
        'avatar': VueAvatar.Avatar
    },
    data: function () {

        return {
            activeIndex2: '1-1',
            computer_oid: '',
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
            }]
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
        }
    },
    mounted() {
        //themleaf模板中将oid赋值给了全局window对象
        this.computer_oid = window.oid;
        this.getModelResourceInfo();
    }
})