new Vue({
    el: '#app',
    components: {
        'avatar': VueAvatar.Avatar
    },
    data: function () {
        return {
            activeIndex2: '1-2',
            list: [{
                "image": "",
                "keywords": ["general physical geography"],
                "createTime": "2019-05-06T12:00:24.604+0000",
                "author": "NNU_Group",
                "name": "DIVA-GIS Country Data",
                "description": "A collection of data collected from a number of the sources below - includes administrative areas, inland water, roads and railways, elevation, land cover, population and climate. Probably the easiest place to get a simple set of data for a specific country.",
                "id": "5cd021d86af45610b4b19572",
                "viewCount": 169
            }, {
                "image": "",
                "keywords": [" watershed boundaries", " drainage directions and flow accumulations for the globe", " river networks"],
                "createTime": "2019-05-07T15:24:32.490+0000",
                "author": "NNU_Group",
                "name": "HydroSHEDS",
                "description": "Hydrological data and maps based on the STRM elevation data. Includes river networks, watershed boundaries, drainage directions and flow accumulations for the globe.",
                "id": "5cd1a3306af45609247f6ecb",
                "viewCount": 51
            }, {
                "image": "",
                "keywords": [" catchments and rivers", "European Union area", "river basins"],
                "createTime": "2019-05-07T15:27:50.341+0000",
                "author": "NNU_Group",
                "name": "Catchment Characterisation and Modelling",
                "description": "Data on river basins, catchments and rivers for the European Union area.",
                "id": "5cd1a3f66af45609247f6ecc",
                "viewCount": 4
            }, {
                "image": "",
                "keywords": ["outlines of major watersheds", "Vector data"],
                "createTime": "2019-05-07T15:30:27.191+0000",
                "author": "NNU_Group",
                "name": "Major Watersheds of the World Deliniation",
                "description": "Vector data showing the outlines of major watersheds (river basins) across the world.",
                "id": "5cd1a4936af45609247f6ecd",
                "viewCount": 5
            }, {
                "image": "",
                "keywords": ["environmental waters", "hydrogen and oxygen isotope composition of precipitation"],
                "createTime": "2019-05-07T15:33:16.607+0000",
                "author": "NNU_Group",
                "name": "Water Isotopes",
                "description": "Global grids of hydrogen and oxygen isotope composition of precipitation and environmental waters in ArcGRID format. Data can be downloaded for whole globe or individual continents.",
                "id": "5cd1a53c6af45609247f6ece",
                "viewCount": 2
            }, {
                "image": "",
                "keywords": ["EC Joint Research Centre"],
                "createTime": "2019-05-07T15:35:58.694+0000",
                "author": "NNU_Group",
                "name": "JRC Water Portal",
                "description": "European water data from the EC Joint Research Centre, including data on quantity, quality, price, use, exploitation and irrigation.",
                "id": "5cd1a5de6af45609247f6ecf",
                "viewCount": 1
            }, {
                "image": "",
                "keywords": ["gridded bathymetric datasets"],
                "createTime": "2019-05-07T15:38:51.446+0000",
                "author": "NNU_Group",
                "name": "General Bathymetric Chart of the Oceans",
                "description": "A range of gridded bathymetric datasets compiled by a group of experts.",
                "id": "5cd1a68b6af45609247f6ed0",
                "viewCount": 1
            }, {
                "image": "",
                "keywords": ["environmental information for freshwater ecosystems", "climate", " land-cover", " soil and geology", "1km-resolution"],
                "createTime": "2019-05-07T15:41:08.883+0000",
                "author": "NNU_Group",
                "name": "EarthEnv Freshwater Ecosystems Environmental Information",
                "description": "1km-resolution environmental information for freshwater ecosystems, covering almost the whole globe. Information includes climate, land-cover, soil and geology.",
                "id": "5cd1a7146af45609247f6ed1",
                "viewCount": 2
            }, {
                "image": "",
                "keywords": ["1998 and 2007", "chlorophyll concentrations from SeaWIFS satellite"],
                "createTime": "2019-05-09T13:47:45.321+0000",
                "author": "NNU_Group",
                "name": "Coastal Water Quality",
                "description": "Quality of coastal waters across the globe measured by chlorophyll concentrations from SeaWIFS satellite. Data for 1998 and 2007.",
                "id": "5cd42f816af4560a78eff7fb",
                "viewCount": 2
            }, {
                "image": "",
                "keywords": ["data from the AGIV"],
                "createTime": "2019-05-09T16:30:11.726+0000",
                "author": "NNU_Group",
                "name": "Agency for Geographical Information of Flanders",
                "description": "A wide range of data from the AGIV, including land cover, aerial photographs, hydrographic data, administrative data and DEMs.",
                "id": "5cd455936af4560a78eff832",
                "viewCount": 5
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