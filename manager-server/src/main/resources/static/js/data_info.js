new Vue({
    el: '#app',
    components: {
        'avatar': VueAvatar.Avatar
    },
    data: function () {

        return {
            activeIndex2: '2',
            modelItem:{
                "image": "",
                "keywords": ["general physical geography"],
                "createTime": "2019-05-06T12:00:24.604+0000",
                "author": "NNU_Group",
                "name": "DIVA-GIS Country Data",
                "description": "A collection of data collected from a number of the sources below - includes administrative areas, inland water, roads and railways, elevation, land cover, population and climate. Probably the easiest place to get a simple set of data for a specific country.",
                "id": "5cd021d86af45610b4b19572",
                "viewCount": 169
            },
            user:{"image": "", "name": "NNU_Group", "oid": "42"},
            tableData1: [{
                date: '2016-05-02',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                date: '2016-05-04',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1517 弄'
            }, {
                date: '2016-05-01',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1519 弄'
            }, {
                date: '2016-05-03',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1516 弄'
            }],
            tableData2: [{
                date: '2016-05-02',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                date: '2016-05-04',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1517 弄'
            }, {
                date: '2016-05-01',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1519 弄'
            }, {
                date: '2016-05-03',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1516 弄'
            }],
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