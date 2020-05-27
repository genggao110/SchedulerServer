Vue.component('headSideBar', {
    template: '#headSideBar',
    props: ['userInfoinParent','curindexParent'],

    data() {
        return {
            tableData: [{
                info:[],
                model:[],
                data:[],
                application:[]
            }],
            message_num:0,
            curIndex:1,
            itemIndex: 1,

            //
            userInfo:{

            },
        }
    },

    watch: {
        curindexParent: {
            handler() {
                this.curIndex = this.curindexParent
            },
            immediate: true
        },

        userInfoinParent: {
            handler() {
                this.userInfo = this.userInfoinParent
                this.getMessageInfo()
            },
            immediate: true
        },

        $route:{
            handler(to, from) {
                //通过路由判断条目高亮显示,
                console.log(to.path)
                let path = to.path
                if(path === '/')
                    this.curIndex = 1
                else if(path.indexOf('project') != -1)
                    this.curIndex = 1
                else if(path.indexOf('resource') != -1)
                    this.curIndex = 2
                else if(path.indexOf('message') != -1)
                    this.curIndex = 3


            },
            immediate:true
        }
    },

    methods:{
        changeRter(index){
            this.curIndex = index;
            var urls={
                1:'/user/userSpace#/project',
                2:'/user/userSpace#/resource',
                3:'/user/userSpace#/message',
            }

            this.setSession('curIndex',index)
            window.location.href=urls[index]

            //此处改完还要修改监听中的$route以保证回退时能够正确高亮显示所在条目

        },

        setSession(name, value) {
            window.sessionStorage.setItem(name, value);
            // this.editOid = sessionStorage.getItem('editItemOid');
        },

        getUserInfo() {
            axios.get('/user/load').then(
                res => {
                    if(res.data.name!=''){
                        this.userInfo = res.data

                        this.load = false;
                    }

                }
            )
        },

        getThemeMessage(){
            $.ajax({
                url: "/theme/getoid",
                async: false,
                success:(data)=>{
                    this.oid = data;
                }
            })
            this.changeRter(10);
        },

        subMenuDropDpwn(target,timerDrop,timerFold){
            let childrenCount=target.children('ul').children('li').length;
            // console.log(childrenCount);
            let timeLength=childrenCount*60;
            let height=childrenCount*45+5;
            // target.animate({height:height},timeLength,'swing');
            target.children('ul').animate({height:height},timeLength,'swing');
            // target.children('ul').animate({width:180},timeLength,'swing');
            // target.children('ul').children().css('display','block')
            let li=target.children('ul').children('li');
            clearTimeout(timerFold);

        },

        subMenuFoldUp(target,timerDrop,timerFold){
            let childrenCount=target.children('ul').children('li').length;
            let timeLength=childrenCount*40;

            // target.animate({height:0},timeLength,'linear');
            target.children('ul').animate({height:0},timeLength,'linear');
            // target.children('ul').animate({width:0},timeLength,'swing');
            let li=target.children('ul').children('li');
            // target.children('ul').children().css('display','none')
            clearTimeout(timerDrop);
            target.children('ul').children('#phoneLogin').css('height','0')
        },

        getMessageInfo(){
            $.ajax({
                url:"/theme/getedit",
                async:false,
                type:"GET",
                success:(json)=>{
                    console.log(json);
                    for (let i=0;i<json.length;i++) {
                        for (let k = 0; k < 4; k++) {
                            let type;
                            switch (k) {
                                case 0:
                                    type = json[i].subDetails;
                                    break;
                                case 1:
                                    type = json[i].subClassInfos;
                                    break;
                                case 2:
                                    type = json[i].subDataInfos;
                                    break;
                                case 3:
                                    type = json[i].subApplications;
                                    break;

                            }
                            if (type != null && type.length > 0) {
                                for (let j = 0; j < type.length; j++) {
                                    if (k == 0) {
                                        switch (type[j].status) {
                                            case "0":
                                                this.message_num++;
                                        }
                                    }else if (k == 1){
                                        switch (type[j].status) {
                                            case "0":
                                                this.message_num++;
                                        }

                                    }else if (k == 2){
                                        switch (type[j].status) {
                                            case "0":
                                                this.message_num++;
                                        }

                                    } else if (k == 3){
                                        switch (type[j].status) {
                                            case "0":
                                                this.message_num++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    $.ajax({
                        type: "GET",
                        url: "/version/getVersions",
                        data: {},
                        async: false,
                        success: (json) => {
                            //下面将type分到model、community中
                            //model：modelItem、conceptualModel、logicalModel、computableModel
                            // community：concept、spatialReference	、unit、template
                            this.message_num = 0
                            for (let i=0;i<json.data.uncheck.length;i++){
                                if (json.data.uncheck[i].type == "modelItem" || json.data.uncheck[i].type == "conceptualModel"||json.data.uncheck[i].type == "logicalModel"||json.data.uncheck[i].type == "computableModel"){
                                    // this.model_tableData1.push(json.data.uncheck[i]);
                                    this.message_num++;
                                }else {
                                    // this.community_tableData1.push(json.data.uncheck[i]);
                                    this.message_num++;
                                }
                            }
                            if (this.message_num==0){
                                $(".el-badge__content").hide();
                            } else {
                                $(".el-badge__content").show();
                            }
                        }
                    })


                }
            })
        }
    },

    created(){
        this.getUserInfo()
    },

    mounted(){
        let that = this;
        //let that= this;
        //用于判断用户是否收到消息
        that.getMessageInfo()

        $('#dropmu').click((e)=>{
            // clearTimeout(tFoldLmu);
            let target=$('#submenu');
            let height=target.children('ul').css('height');
            if (height=='0px'){
                target.css('display','block')
                this.subMenuDropDpwn(target);
            }

            else
                this.subMenuFoldUp(target);
            if(e.stopPropagation){
                e.stopPropagation();
            }else{
                e.cancelBubble = true;
            }
        })

        $('html').click((e)=>{
            if($(e.target).closest("#leftUl").length == 0){
                // clearTimeout(tFoldLmu);
                let target=$('#submenu');
                this.subMenuFoldUp(target);
            }
        })
    },

})