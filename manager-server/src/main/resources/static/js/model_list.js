new Vue({
    el: '#app',
    components: {
        'avatar': VueAvatar.Avatar
    },
    data: function () {
        return {
            activeIndex2: '1-1',
            searchText: '',
            pageOption: {
                paginationShow: false,
                progressBar: true,
                sortAsc: false,
                currentPage: 1,
                pageSize: 10,
                total: 264,
                searchResult: [],
            },

        };
    },
    methods: {
        handleSelect(key, keyPath) {
            console.log(key, keyPath);
        },
        handlePageChange(val) {
            this.pageOption.currentPage = val;
            window.scrollTo(0,0);
            this.searchResult();
        },

        searchResult(){
            this.pageOption.progressBar = true;
            var data = {
                asc: this.pageOption.sortAsc,
                page: this.pageOption.currentPage - 1,
                pageSize: this.pageOption.pageSize,
                type: 'Package'
            };
            let search_text = this.searchText.trim();
            data.searchText = search_text;
            $.ajax({
                type: "POST",
                url: "/modelResource/list",
                data: data,
                async: true,
                success: (json) => {
                    if(json.code == 0){
                        //获取数据成功
                        let result = json.data;
                        this.pageOption.total = result.total;
                        this.pageOption.pages = result.pages;
                        this.pageOption.searchResult = result.resource;
                        this.pageOption.users = result.users;
                        this.pageOption.progressBar = false;
                        this.pageOption.paginationShow = true;
                    }else {
                        console.log("query error!");
                    }
                }
            })
        }
    },
    mounted() {
        //更新页面获取数据
        this.searchResult();

        $(function () {


        })
    }
})