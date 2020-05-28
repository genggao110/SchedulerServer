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

            invites:[],
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
                this.getInvites()
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

        getInvites(){
            $.ajax({
                type: "GET",
                url: "/user/getInvite",
                data: {},
                cache: false,
                async: false,
                xhrFields: {
                    withCredentials: true
                },
                crossDomain: true,
                success:(res)=>{
                    if(res.code == -1){
                        alert("Please login");
                        window.location.href = "/user/login";
                    }else{
                        let data = res.data;
                        this.invites = data;
                        this.bbSort(this.invites,'createDate')

                        this.invitesAccept = this.invites.filter((ele)=>{
                            return ele.status==1
                        })
                        this.invitesUnapprv = this.invites.filter((ele)=>{
                            return ele.status==0
                        })

                    }
                }
            })
        },
    },

    created(){
        this.getUserInfo()
    },

    mounted(){
        let that = this;
        //let that= this;
        //用于判断用户是否收到消息
        this.getInvites();

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