var userProjects = Vue.extend(
    {
        template:'#userProjects',
        data() {
            return {
                //页面样式控制
                loading: 'false',
                load: true,
                ScreenMinHeight: "0px",
                ScreenMaxHeight: "0px",

                //显示控制
                curIndex: 1,

                itemIndex: 1,
                //
                userInfo: {},

                resourceLoad: false,

                //分页控制
                page: 1,
                sortAsc: 1,//1 -1
                sortType: "default",
                pageSize: 10,// 每页数据条数
                totalPage: 0,// 总页数
                curPage: 1,// 当前页码
                pageList: [],
                totalNum: 0,

                pageDialog: 1,
                totalPageDialog: 0,
                curPageDialog: 1,
                pageListDialog: [],

                //用户
                userId: -1,

                //展示变量\
                itemTitle: 'Model Item',

                searchResult: [],
                modelItemResult: [],

                searchCount: 0,
                ScreenMaxHeight: "0px",
                searchText: "",

                isInSearch: 0,

                //task相关
                projectStatus: 'all',
                options: [
                    {
                        value: 'all',
                        label: 'all',

                    },
                    {
                        value: 'ready',
                        label: 'ready',

                    },

                    {
                        value: 'preparing',
                        label: 'preparing',

                    },
                ],
                addOutputToMyDataVisible: false,
                taskSharingVisible:false,
                taskSharingActive:0,
                taskDataList: [],
                stateFilters: [],
                multipleSelection: [],
                multipleSelectionMyData: [],
                taskCollapseActiveNames: [],
                taskDataForm: {
                    name: '',
                    type: "option1",
                    contentType: "resource",
                    description: "",
                    detail: "",
                    reference: "",
                    author: "",
                    keywords: [],
                    contributers: [],
                    classifications: [],
                    displays: [],
                    authorship: [],
                    comments: [],
                    dataList: [],

                    categoryText: [],

                },

                categoryTree: [],

                folderTree: [{
                    id: 1,
                    label: 'All Folder',
                    children: [{
                        id: 4,
                        label: '二级 1-1',
                        children: [{
                            id: 9,
                            label: '三级 1-1-1'
                        }, {
                            id: 10,
                            label: '三级 1-1-2'
                        }]
                    }]
                }],

                folderTree2: [{
                    id: 1,
                    label: 'All Folder',
                    children: [{
                        id: 4,
                        label: '二级 1-1',
                        children: [{
                            id: 9,
                            label: '三级 1-1-1'
                        }, {
                            id: 10,
                            label: '三级 1-1-2'
                        }]
                    }]
                }],
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

                modelContainerList:[],

                deployDialog:false,
                userChooseDialog:false,

                loadingUser:{},

                addProjectStep:0,

                resources:{},

                modelResourceInfo:[],

                modelTable:[],

                selectedModel:{
                    name:'',
                    createTime:'',
                },

                //loading控制
                loadModel:false,
                searchLoading:false,

                isInModelSearch:0,

                searchModelText:'',

                modelContainerUse : [],
                modelContainerListInviting:[

                ],

                invitedUsers: [{
                    oid: '42',
                    name: 'NNU_Group',
                    email:'',
                    userName:'njgis',
                },  ],
                invitingUsers:[

                ],

                searchUserText:'',

                projectTitle:'',
                projectDecription:'',
            }
        },

        components: {},

        methods: {
            //公共功能
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

            setSession(name, value) {
                window.sessionStorage.setItem(name, value);
                // this.editOid = sessionStorage.getItem('editItemOid');
            },

            //page
            // 初始化page并显示第一页
            pageInit() {
                this.totalPage = Math.floor((this.totalNum + this.pageSize - 1) / this.pageSize);
                if (this.totalPage < 1) {
                    this.totalPage = 1;
                }
                this.getPageList();
                this.changePage(1);
            },

            getPageList() {
                this.pageList = [];

                if (this.totalPage < 5) {
                    for (let i = 0; i < this.totalPage; i++) {
                        this.pageList.push(i + 1);
                    }
                } else if (this.totalPage - this.curPage < 5) {//如果总的页码数减去当前页码数小于5（到达最后5页），那么直接计算出来显示

                    this.pageList = [
                        this.totalPage - 4,
                        this.totalPage - 3,
                        this.totalPage - 2,
                        this.totalPage - 1,
                        this.totalPage,
                    ];
                } else {
                    let cur = Math.floor((this.curPage - 1) / 5) * 5 + 1;
                    if (this.curPage % 5 === 0) {
                        cur = cur + 1;

                    }
                    this.pageList = [
                        cur,
                        cur + 1,
                        cur + 2,
                        cur + 3,
                        cur + 4,
                    ]
                }
            },

            changePage(pageNo) {
                if ((this.curPage === 1) && (pageNo === 1)) {
                    return;
                }
                if ((this.curPage === this.totalPage) && (pageNo === this.totalPage)) {
                    return;
                }
                if ((pageNo > 0) && (pageNo <= this.totalPage)) {
                    if (this.curIndex != 1)
                        this.pageControlIndex = this.curIndex;
                    else this.pageControlIndex = 'research';

                    this.resourceLoad = true;
                    this.searchResult = [];
                    //not result scroll
                    //window.scrollTo(0, 0);
                    this.curPage = pageNo;
                    this.getPageList();
                    this.page = pageNo;


                    if (this.isInSearch == 0)
                        this.showProjectByStatus(this.projectStatus);
                    else this.searchProject()
                }
            },

            // dialog和外层是两套翻页
            pageInitDialog() {
                this.totalPageDialog = Math.floor((this.totalNum + this.pageSize - 1) / this.pageSize);
                if (this.totalPageDialog < 1) {
                    this.totalPageDialog = 1;
                }
                this.getpageListDialog();
                this.changePage(1);
            },

            getpageListDialog() {
                this.pageListDialog = [];

                if (this.totalPageDialog < 5) {
                    for (let i = 0; i < this.totalPageDialog; i++) {
                        this.pageListDialog.push(i + 1);
                    }
                } else if (this.totalPageDialog - this.curPageDialog < 5) {//如果总的页码数减去当前页码数小于5（到达最后5页），那么直接计算出来显示

                    this.pageListDialog = [
                        this.totalPageDialog - 4,
                        this.totalPageDialog - 3,
                        this.totalPageDialog - 2,
                        this.totalPageDialog - 1,
                        this.totalPageDialog,
                    ];
                } else {
                    let cur = Math.floor((this.curPageDialog - 1) / 5) * 5 + 1;
                    if (this.curPageDialog % 5 === 0) {
                        cur = cur + 1;

                    }
                    this.pageListDialog = [
                        cur,
                        cur + 1,
                        cur + 2,
                        cur + 3,
                        cur + 4,
                    ]
                }
            },

            changePageDialog(pageNo) {
                if ((this.curPageDialog === 1) && (pageNo === 1)) {
                    return;
                }
                if ((this.curPageDialog === this.totalPageDialog) && (pageNo === this.totalPageDialog)) {
                    return;
                }
                if ((pageNo > 0) && (pageNo <= this.totalPageDialog)) {
                    if (this.curIndex != 1)
                        this.pageControlIndex = this.curIndex;
                    else this.pageControlIndex = 'research';

                    this.resourceLoad = true;
                    this.searchResult = [];
                    //not result scroll
                    //window.scrollTo(0, 0);
                    this.curPageDialog = pageNo;
                    this.getpageListDialog();
                    this.pageDialog = pageNo;


                    if (this.isInModelSearch == 0)
                        this.getModelResourceInfo('');
                    else this.searchModelResourceInfo()
                }
            },

            // creatItem(index){
            //     window.sessionStorage.removeItem('editOid');
            //     if(index == 1) window.location.href='../model/createModelItem'
            // },

            reloadPage() {//重新装订分页诸元
                this.pageSize = 10;
                this.isInSearch = 0;
                this.page = 1;
            },

            deleteItem(id) {
                //todo 删除category中的 id
                var cfm = confirm("Are you sure to delete?");

                if (cfm == true) {
                    axios.get("/dataItem/del/", {
                        params: {
                            id: id
                        }
                    }).then(res => {
                        if (res.status == 200) {
                            alert("delete success!");
                            this.getDataItems();
                        }
                    })
                }
            },

            addProject(){
                this.deployDialog = true;
                this.getModelResourceInfo('')
                this.addProjectStep = 0
            },

            searchModelResourceInfo(){
                this.searchLoading = true
                this.isInModelSearch = 1;
                this.getModelResourceInfo(this.searchModelText)
            },

            getModelResourceInfo(searchText) {
                let url = '/modelResource/list' ;
                $.ajax({
                    type: "POST",
                    url: url,
                    data: {},
                    async: false,
                    cache: false,
                    crossDomain: true,
                    data:{
                        searchText:searchText,
                        type:'Package',
                        page:this.pageDialog-1,
                    },
                    success: (data) => {
                        if(data.code == 0){
                            this.totalNum = data.data.total
                            let result = data.data.resource;
                            console.log(result);
                            this.modelResourceInfo = result;
                            setTimeout(()=>{
                                this.modelTable = result;
                                this.searchLoading = false
                            },180)
                            if (this.pageDialog == 1) {
                                this.pageInitDialog();
                            }
                            this.resources = result.resources;
                            if(result.runtime!=null){
                                let runtime_json = JSON.parse(result.runtime);
                                //判断哪个是新老版本
                                if(runtime_json.platform == null){
                                    this.oldtableData = runtime_json;
                                    this.oldtableStatus = true;
                                }else {
                                    this.tableData = runtime_json;
                                }
                                console.log(runtime_json);
                            }


                        }else{
                            this.$message({
                                message: '获取模型资源详细描述信息失败',
                                type: 'error'
                            });
                        }
                    }
                })
            },

            changeProjectStatus(status) {
                this.projectStatus = status;
                this.showProjectByStatus(this.projectStatus)
            },

            selectModel(model){
                this.loadModel = true;
                setTimeout(()=>{
                    this.selectedModel = model;
                    this.loadModel = false;
                },200)

            },

            showProjectByStatus(status) {
                let name = 'tasks'
                this.projectStatus = status
                this.isInSearch = 0;
                if (this.projectStatus === 'ready')
                    $('.wzhSelectContainer input').css('background', '#63b75d')
                else if (this.projectStatus === 'all')
                    $('.wzhSelectContainer input').css('background', '#00ABFF')
                else if (this.projectStatus === 'preparing')
                    $('.wzhSelectContainer input').css('background', '#1caf9a')
                axios.get("/project/getProjectsByUserIdByStatus", {
                        params: {
                            status: status,
                            page: this.page - 1,
                            sortType: this.sortType,
                            asc: -1,
                        }
                    }


                    ,).then(
                    res => {
                        if (res.data.code != 0) {
                            alert("Please login first!");
                            window.location.href = "/user/login";
                        } else {
                            const data = res.data.data;
                            this.resourceLoad = false;
                            this.totalNum = data.count;
                            this.searchResult = data.content
                            //this.modelItemResult = data[name];
                            if (this.page == 1) {
                                this.pageInit();
                            }
                        }
                    }
                )

                this.activeIndex = '6'
                // this.curIndex = '6'
                this.defaultActive = '6';
                this.pageControlIndex = '6';
            },


            searchProjects() {
                let url = "/project/searchProjects";
                this.isInSearch = 1;
                this.projectStatus = 'all'
                $.ajax({
                    type: "Get",
                    url: url,
                    data: {
                        searchText: this.searchText,
                        page: this.page - 1,
                        pagesize: this.pageSize,
                        sortType: this.sortType,
                        asc: this.sortAsc
                    },
                    cache: false,
                    async: true,

                    success: (json) => {
                        if (json.code != 0) {
                            alert("Please login first!");
                            window.location.href = "/user/login";
                        } else {
                            data = json.data;
                            this.resourceLoad = false;
                            this.totalNum = data.count;
                            this.searchResult = data.content
                            //this.modelItemResult = data[name];
                            if (this.page == 1) {
                                this.pageInit();
                            }

                        }

                    }
                })
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

            deployModel(){

                let hrefs = window.location.href.split('/')
                window.sessionStorage.setItem('oid',hrefs[hrefs.length-1])
                window.location.href='/modelResource/createProject'
            },

            DeployDialog(){

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

            searchUser(type){
                $.ajax({
                    type: "GET",
                    url:'/portal/user/searchUserByType',
                    data:{
                        searchText:this.searchUserText,
                        type:type
                    },
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
                    title:this.projectTitle,
                    description:this.projectDecription,
                    owner:this.loadingUser.userName,
                    invitedUsers:this.tableData1,
                    invitingUsers:this.chosenUser,
                    model:this.selectedModel.oid,
                    modelName:this.selectedModel.name,
                }
                $.ajax({
                    url: "/project/create",
                    async: true,
                    type: "POST",
                    contentType: 'application/json',
                    data: JSON.stringify(obj),

                    success:(res)=>{
                        if(res=="-1"){
                            this.$alert('Please log in first', 'warning', {
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
                                window.open ("/project/" + res) ;
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

                            this.showProjectByStatus('all')
                        }
                    }
                })
            },

            addProjectPre(){
                this.addProjectStep--
            },

            addProjectNext(){
                if(this.projectTitle == ''&&this.addProjectStep==0){
                    this.$alert('Please input the title first!',{
                        confirmButtonText: 'OK',
                    })
                    return
                }
                if(this.selectedModel.name == ''&&this.addProjectStep==1){
                    this.$alert('Please select a model first!',{
                        confirmButtonText: 'OK',
                    })
                    return
                }
                this.addProjectStep++
                if(this.addProjectStep==2){
                    let invited = [],inviting = [];
                    this.tableData1.forEach((ele)=>{
                        invited.push(ele.userName)
                    })
                    this.chosenUser.forEach((ele)=>{
                        inviting.push(ele.userName)
                    })
                    this.modelContainerUse =  this.getAvailableComputerResourceByUser(invited)
                }

            },

            //展开task详细数据
            expandRunInfo(index,$event){
                if(!$('.ab').eq(index).hasClass('transform180')){
                    $('.ab').eq(index).addClass('transform180')
                    $('.modelRunInfo').eq(index).collapse('show')
                }else {
                    $('.ab').eq(index).removeClass('transform180')
                    $('.modelRunInfo').eq(index).collapse('hide')
                }

            },

            sendcurIndexToParent(){
                this.$emit('com-sendcurindex',this.curIndex)
            },

            sendUserToParent(userId){
                this.$emit('com-senduserinfo',userId)
            },


        },


        created() {


        },

        mounted() {

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

                        console.log(data);

                        if (data.oid == "") {
                            alert("Please login");
                            window.location.href = "/user/login";
                        } else {
                            this.userId = data.oid;
                            this.userName = data.name;
                            this.loadingUser = data
                            console.log(this.userId)
                            this.tableData1.push(this.loadingUser);
                            this.sendUserToParent(this.userId)

                            $("#author").val(this.userName);

                            var index = window.sessionStorage.getItem("index");
                            //判断显示哪一个item
                            var itemIndex = window.sessionStorage.getItem("itemIndex");
                            this.itemIndex = itemIndex

                            this.showProjectByStatus('all');

                            // if (index != null && index != undefined && index != "" && index != NaN) {
                            //     this.defaultActive = index;
                            //     window.sessionStorage.removeItem("index");
                            //     // this.curIndex = index
                            //
                            //
                            // } else {
                            //     // this.changeRter(1);
                            // }

                            window.sessionStorage.removeItem("tap");
                            //this.getTasksInfo();
                            this.load = false;
                        }
                    }
                })


                //this.getModels();
            });

            //初始化的时候吧curIndex传给父组件，来控制bar的高亮显示
            this.sendcurIndexToParent()

            var that = this
            //获取data item分类树

        },

    }
)