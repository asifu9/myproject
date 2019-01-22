
import { Route } from "@angular/router";
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { FeedsComponent } from "./module/feed/feeds.component";
import { MessagesComponent } from "./module/messages/messages/messages.component";
import { GroupsComponent } from "./module/groups/groups/groups.component";
import { NewgroupComponent } from "./module/groups/groups/newgroup.component";
import { PhotoalbumsComponent } from "./module/photo/photoalbums/photoalbums.component";
import { PhotosComponent } from "./module/photo/photos/photos.component";
import { NotificationComponent } from "./module/notification/notification/notification.component";
import { ReqNotifyComponent } from "./module/notification/notification/req-notify/req-notify.component";
import { LikeNotifyComponent } from "./module/notification/notification/like-notify/like-notify.component";
import { GroupLeftComponent } from "./module/groups/groupfeed/groupleft.component";
import { GroupMiddleComponent } from "./module/groups/groupfeed/groupmiddle.component";
import { GroupusersComponent } from "./module/groups/groupfeed/groupusers.component";
import { AdminComponent } from "./module/admin/admin/admin/admin.component";
import { CompanyComponent } from "./module/admin/admin/company/company.component";
import { SelectedCompanyComponent } from "./module/admin/admin/company/selectedcompany.component";
import { UsersComponent } from "./module/admin/admin/users/users.component";
import { NewUserComponent } from "./module/admin/admin/users/newuser.component";
import { ProfileComponent } from "./module/profile/profile/profile.component";
import { AddressComponent } from "./module/profile/profile/items/address.component";
import { ProfessionComponent } from "./module/profile/profile/items/profession.component";
import { EducationComponent } from "./module/profile/profile/items/education.component";
import { AdminSettingComponent } from "./module/admin-setting/admin-setting.component";
import { DepartmentComponent } from "./module/admin-setting/department/department.component";
import { DesignationComponent } from "./module/admin-setting/designation/designation.component";
import { OrganizationComponent } from "./module/admin-setting/organization/organization.component";
import { OrganizationCreateComponent } from "./module/admin-setting/organization-create/organization-create.component";
import { DesignationCreateComponent } from "./module/admin-setting/designation-create/designation-create.component";
import { DepartmentCreateComponent } from "./module/admin-setting/department-create/department-create.component";
import { UserComponent } from "./module/admin-setting/user/user.component";
import { UserListComponent } from "./module/admin-setting/user/user-list/user-list.component";
import { UserCreateComponent } from "./module/admin-setting/user/user-create/user-create.component";
import { UserDetailsComponent } from "./module/admin-setting/user/user-create/user-details/user-details.component";
import { UserBasicComponent } from "./module/admin-setting/user/user-create/user-basic/user-basic.component";
import { UserPhotoComponent } from "./module/admin-setting/user/user-create/user-photo/user-photo.component";
import { UserLinkComponent } from "./module/admin-setting/user/user-create/user-link/user-link.component";
import { UserSettingComponent } from "./module/admin-setting/user/user-create/user-setting/user-setting.component";
import { UserCredentialComponent } from "./module/admin-setting/user/user-create/user-credential/user-credential.component";
import { TeamComponent } from "./module/admin-setting/team/team.component";
import { TeamCreateComponent } from "./module/admin-setting/team-create/team-create.component";
import { PhotoalbumComponent } from "./module/photo/photoalbums/photoalbum.component";
import { TicketHomeComponent } from "./module/ticket/ticket-home/ticket-home.component";
import { TicketListComponent } from "./module/ticket/ticket-list/ticket-list.component";
import { TicketCreateComponent } from "./module/ticket/ticket-create/ticket-create.component";
import { TicketCategoryHomeComponent } from "./ticket-category/ticket-category-home/ticket-category-home.component";
import { TicketCategoryListComponent } from "./ticket-category/ticket-category-list/ticket-category-list.component";
import { TicketCategoryCreateComponent } from "./ticket-category/ticket-category-create/ticket-category-create.component";
import { TicketAssignComponent } from "./module/ticket/ticket-assign/ticket-assign.component";
import { AuthguardGuard } from "./authguard.guard";
import { ActivityGuard } from "./ActivityGuard";
import { AccessRestrictionComponent } from "./module/shared/access-restriction/access-restriction.component";
import { UserActivitiesComponent } from "./module/admin-setting/user/user-create/user-activities/user-activities.component";
import { EventsHomeComponent } from "./module/events/events-home/events-home.component";
import { EventsListComponent } from "./module/events/events-list/events-list.component";
import { EventsCreateComponent } from "./module/events/events-create/events-create.component";
import { FormViewComponent } from "./module/form/form-view/form-view.component";
import { ProjectHomeComponent } from "./module/project/project-home/project-home.component";
import { ProjectCreateComponent } from "./module/project/project-create/project-create.component";
import { ProjectListComponent } from "./module/project/project-list/project-list.component";
import { LovHomeComponent } from "./module/admin-setting/lov/lov-home/lov-home.component";
import { LovCreateComponent } from "./module/admin-setting/lov/lov-create/lov-create.component";
import { LovListComponent } from "./module/admin-setting/lov/lov-list/lov-list.component";
import { LoginPageComponent } from "./module/login-module/login-page/login-page.component";
import { BasePageComponent } from "./base-page/base-page.component";
import { RolesComponent } from "./module/admin-setting/roles/roles.component";
import { RoleCreateComponent } from "./module/admin-setting/role-create/role-create.component";
import { MesdetailComponent } from "./module/messages/mesdetail/mesdetail.component";
import { CompanyCreateComponent } from "./module/admin/admin/company-create/company-create.component";
import { UserMapActionComponent } from "./module/admin-setting/user/user-create/user-map-action/user-map-action.component";
import { LeaveTypeComponent } from "./module/admin-setting/leave-type/leave-type.component";
import { LeaveTypeCreateComponent } from "./module/admin-setting/leave-type-create/leave-type-create.component";
import { CompanySettingComponent } from "./module/admin-setting/company-setting/company-setting.component";
import { LeaveDetailsComponent } from "./module/leaves/leave-details/leave-details.component";
import { NotificationListComponent } from "./module/shared/shared/notification-list/notification-list.component";
import { LeaveManageComponent } from "./module/leaves/leave-manage/leave-manage.component";
import { LeaveCreateComponent } from "./module/leaves/leave-create/leave-create.component";
import { DisplaySettingComponent } from "./module/admin-setting/display-setting/display-setting.component";


export const routeConfig = [
    {
        path: ":compName/login",
        component: LoginPageComponent
    },
    {
        path: "group/:name",
        component: GroupLeftComponent
        , canActivate: [AuthguardGuard],
        children: [
            { path: "", redirectTo: "feed", pathMatch: "full" },
            {
                path: "feed",

                component: GroupMiddleComponent
                , pathMatch: "full"
                //  ,outlet: 'groupMain'
            },
            {
                path: "members",
                component: GroupusersComponent

                //           outlet: 'groupMain'
            }
        ]
    },
    {
        path: "home"
        , component: HomeComponent
        , canActivate: [AuthguardGuard]
        , children: [

            {
                path: "feed",

                component: FeedsComponent, data: {
                    mainRole: 'feed',
                    subRole: null
                }
            }, {
                path: "leaves",
                component: LeaveDetailsComponent, data: {
                    mainRole: 'feed',
                    subRole: null
                }
            }, {
                path: "leaves/create",
                component: LeaveCreateComponent, data: {
                    mainRole: 'feed',
                    subRole: null
                }
            }, {
                path: "leaves/edit/:id",
                component: LeaveCreateComponent, data: {
                    mainRole: 'feed',
                    subRole: null
                }
            }, {
                path: "notifications",
                component: NotificationListComponent, data: {
                    mainRole: 'feed',
                    subRole: null
                }
            },
            {
                path: "form/:name",
                component: FormViewComponent, data: {
                    mainRole: 'feed',
                    subRole: null
                }
            },
            {
                path: "noaccess",
                component: AccessRestrictionComponent,
                data: {
                    mainRole: 'noaccess'
                }
            }, {
                path: "messages",
                component: MessagesComponent, data: {
                    mainRole: 'message',
                    subRole: null
                },
                children: [
                    {
                        path: ":type/:id",
                        component: MesdetailComponent, data: {
                            mainRole: 'message',
                            subRole: null
                        }
                    }
                ]
            }
            , {
                path: "group",
                component: GroupsComponent,
                data: {
                    mainRole: 'group',
                    subRole: null
                }

            }, {
                path: "group/new",
                component: NewgroupComponent,
                data: {
                    mainRole: 'group',
                    subRole: 'creategroup'
                }
            }, {
                path: "project",
                component: ProjectHomeComponent,
                data: {
                    mainRole: 'group',
                    subRole: null
                }
            }, {
                path: "project/create",
                component: ProjectCreateComponent,
                data: {
                    mainRole: 'ticket',
                    subRole: null
                }
            }, {
                path: "project/update/:id",
                component: ProjectCreateComponent,
                data: {
                    mainRole: 'ticket',
                    subRole: null
                }
            }, {
                path: "project/list",
                component: ProjectListComponent,
                data: {
                    mainRole: 'ticket',
                    subRole: null
                }
            }


            ,
            {
                path: "events",
                component: EventsHomeComponent,
                data: {
                    mainRole: 'group',
                    subRole: null
                },
                children: [
                    {
                        path: "", redirectTo: "list", pathMatch: "full", data: {
                            mainRole: 'ticket',
                            subRole: null
                        }
                    },
                    {
                        path: "list",
                        component: EventsListComponent,
                        data: {
                            mainRole: 'ticket',
                            subRole: null
                        }
                    }, {
                        path: "create",
                        component: EventsCreateComponent,
                        data: {
                            mainRole: 'ticket',
                            subRole: null
                        }
                    }, {
                        path: "edit/:id",
                        component: EventsCreateComponent,
                        data: {
                            mainRole: 'ticket',
                            subRole: null
                        }
                    }
                ]

            }
            , {
                path: "albums",

                component: PhotoalbumsComponent,
                data: {
                    mainRole: 'album',
                    subRole: null
                }

            }, {
                path: "albums/new",

                component: PhotoalbumComponent
                , data: {
                    mainRole: 'album',
                    subRole: 'createalbum'
                }

            }, {
                path: "tickets",
                component: TicketHomeComponent,
                data: {
                    mainRole: 'ticket',
                    subRole: null
                },
                children: [
                    { path: "", redirectTo: "list", pathMatch: "full" },
                    {
                        path: "list",
                        component: TicketListComponent,
                        data: {
                            mainRole: 'ticket',
                            subRole: null
                        }
                    }, {
                        path: "list/:type",
                        component: TicketListComponent,
                        data: {
                            mainRole: 'ticket',
                            subRole: null
                        }
                    }, {
                        path: "create",
                        component: TicketCreateComponent,
                        data: {
                            mainRole: 'ticket',
                            subRole: 'createticket'
                        }
                    }, {

                        path: "update/:id",
                        component: TicketCreateComponent,
                        data: {
                            mainRole: 'editticket',
                            subRole: null
                        }
                    }, {
                        path: "update/ticket/:ticketId",
                        component: TicketAssignComponent,
                        data: {
                            mainRole: 'ticket',
                            subRole: 'assignticket'
                        }
                    }
                ]
            },
            {
                path: "albums/photos/:id",
                component: PhotosComponent,
                data: {
                    mainRole: 'album',
                    subRole: 'uploadphotos'
                }
            },

            {
                path: "notification",
                component: NotificationComponent,
                children: [
                    {
                        path: "",
                        component: ReqNotifyComponent
                    },
                    {
                        path: "request",
                        component: ReqNotifyComponent
                    },
                    {
                        path: "like",
                        component: LikeNotifyComponent
                    }
                ]
            }
            // , {
            //     path: "", redirectTo: "feed", pathMatch: "full", data: {
            //         mainRole: 'feed',
            //         subRole: null
            //     }
            // }

        ]

    }, {
        path: "admin1",

        component: AdminComponent,
        canActivateChild: [ActivityGuard],
        children: [

            {
                path: "",
                component: CompanyComponent,
                data: {
                    mainRole: 'admin',
                    subRole: 'adminaccess'
                }
            },


            {
                path: "user",
                component: UsersComponent,
                data: {
                    mainRole: 'user',
                    subRole: 'viewuser'
                }
            },
            {
                path: "user/:id",
                component: NewUserComponent,
                data: {
                    mainRole: 'user',
                    subRole: 'edituser'
                }
            },
            {
                path: "usernew",
                component: NewUserComponent,
                data: {
                    mainRole: 'user',
                    subRole: 'createuser'
                }
            }
        ]
    }, {
        path: "admin",
        component: AdminSettingComponent,
        canActivateChild: [ActivityGuard],
        children: [
            {
                path: "", redirectTo: "user", pathMatch: "full",
                data: {
                    mainRole: 'organization',
                    subRole: 'vieworganization',
                }
            },
            {
                path: "company",
                component: CompanyComponent,
                data: {
                    mainRole: 'admin',
                    subRole: 'adminaccess'
                }
            }, {
                path: "company/new",
                component: CompanyCreateComponent,
                data: {
                    mainRole: 'admin',
                    subRole: 'adminaccess'
                }
            }, {
                path: "company/edit/:id",
                component: CompanyCreateComponent,
                data: {
                    mainRole: 'admin',
                    subRole: 'adminaccess'
                }
            },
            /*
            {
                path: "project",
                component: ProjectHomeComponent,
                data: {
                    mainRole: 'group',
                    subRole: null
                },
                children: [
                    {
                        path: "create",
                        component: ProjectCreateComponent,
                        data: {
                            mainRole: 'ticket',
                            subRole: null
                        }
                    }, {
                        path: "edit/:id",
                        component: ProjectCreateComponent,
                        data: {
                            mainRole: 'ticket',
                            subRole: null
                        }
                    }, {
                        path: "list",
                        component: ProjectListComponent,
                        data: {
                            mainRole: 'ticket',
                            subRole: null
                        }
                    }
                ]

            }

            */
            {
                path: "project",
                component: ProjectListComponent,
                data: {
                    mainRole: 'ticket',
                    subRole: null
                }
            },
            {
                path: "project/create",
                component: ProjectCreateComponent,
                data: {
                    mainRole: 'ticket',
                    subRole: null
                }
            }, {
                path: "project/update/:id",
                component: ProjectCreateComponent,
                data: {
                    mainRole: 'ticket',
                    subRole: null
                }
            },
            {
                path: "noaccess",
                component: AccessRestrictionComponent,
                data: {
                    mainRole: "noaccess"
                }
            }, {
                path: "ticketCategory",
                component: TicketCategoryListComponent,
                data: {
                    mainRole: 'organization',
                    subRole: 'vieworganization'
                }
            }, {
                path: "ticketCategory/create",
                component: TicketCategoryCreateComponent,
                data: {
                    mainRole: 'organization',
                    subRole: 'vieworganization'
                }
            }, {
                path: "ticketCategory/update/:id",
                component: TicketCategoryCreateComponent,
                data: {
                    mainRole: 'organization',
                    subRole: 'vieworganization'
                }

            },
            {
                path: "role",
                component: RolesComponent,
                canActivateChild: [ActivityGuard],
                data: {
                    mainRole: 'department',
                    subRole: 'viewdepartment'
                }

            },
            {
                path: "role/create",
                component: RoleCreateComponent,
                canActivateChild: [ActivityGuard],
                data: {
                    mainRole: 'department',
                    subRole: 'viewdepartment'
                }

            },
            {
                path: "role/update/:id",
                component: RoleCreateComponent,
                data: {
                    mainRole: 'department',
                    subRole: 'editdepartment'
                }
            },
            {
                path: "department",
                component: DepartmentComponent,
                canActivateChild: [ActivityGuard],
                data: {
                    mainRole: 'department',
                    subRole: 'viewdepartment'
                }

            }, {
                path: "department/update/:id",
                component: DepartmentCreateComponent,
                data: {
                    mainRole: 'department',
                    subRole: 'editdepartment'
                }
            }, {
                path: "department/create",
                component: DepartmentCreateComponent,
                data: {
                    mainRole: 'department',
                    subRole: 'createdepartment',

                }
            }, {
                path: "designation",
                component: DesignationComponent,
                canActivateChild: [ActivityGuard],
                data: {
                    mainRole: 'designation',
                    subRole: 'viewdesignation',
                }
            },
            {
                path: "designation/update/:id",
                component: DesignationCreateComponent,
                data: {
                    mainRole: 'designation',
                    subRole: 'editdesignation'
                }
            }

            , {
                path: "designation/create",
                component: DesignationCreateComponent,
                data: {
                    mainRole: 'designation',
                    subRole: 'createdesignation'
                }
            }


            , {
                path: "team",
                component: TeamComponent,
                canActivateChild: [ActivityGuard],
                data: {
                    mainRole: 'team',
                    subRole: 'viewteam',
                }
            },
            {
                path: "team/create",
                component: TeamCreateComponent,
                data: {
                    mainRole: 'team',
                    subRole: 'createteam'
                }
            }, {
                path: "team/update/:id",
                component: TeamCreateComponent,
                data: {
                    mainRole: 'team',
                    subRole: 'editteam'
                }
            },
                {
                path: "view",
                component: DisplaySettingComponent,
                data: {
                    mainRole: 'team',
                    subRole: 'createteam'
                }
            },
            {
                path: "lov",
                component: LovListComponent,
                data: {
                    mainRole: 'team',
                    subRole: 'createteam'
                }
            }

            , {
                path: "lov/create",
                component: LovCreateComponent,
                data: {
                    mainRole: 'team',
                    subRole: 'createteam'
                }
            }, {
                path: "lov/update/:id",
                component: LovCreateComponent,
                data: {
                    mainRole: 'team',
                    subRole: 'editteam'
                }
            },

            {
                path: "user",
                component: UserListComponent,
                data: {
                    mainRole: 'user',
                    subRole: 'viewuser',
                }
            }, {
                path: "user/create",
                component: UserCreateComponent,
                data: {
                    mainRole: 'user',
                    subRole: 'createuser',
                }

            }, {
                path: "user/details/:id",
                component: UserDetailsComponent,
                canActivateChild: [ActivityGuard],
                data: {
                    mainRole: 'user',
                    subRole: 'edituser',
                },
                children: [
                    {
                        path: "", redirectTo: "edit", pathMatch: "full", data: {
                            shouldDetach: true, mainRole: 'user',
                            subRole: 'edituser'
                        }
                    },
                    {
                        path: "edit",

                        component: UserBasicComponent, data: {
                            shouldDetach: true, mainRole: 'user',
                            subRole: 'edituser'
                        }
                    },
                    {
                        path: "photo",

                        component: UserPhotoComponent, data: {
                            shouldDetach: true, mainRole: 'user',
                            subRole: 'edituser'
                        }
                    },
                    {
                        path: "link",
                        component: UserLinkComponent, data: {
                            shouldDetach: true, mainRole: 'user',
                            subRole: 'edituser'
                        }
                    },
                    {
                        path: "setting",
                        component: UserSettingComponent, data: {
                            shouldDetach: true, mainRole: 'user',
                            subRole: 'edituser'
                        }
                    },
                    {
                        path: "credential",
                        component: UserCredentialComponent, data: {
                            shouldDetach: true, mainRole: 'user',
                            subRole: 'edituser'
                        }
                    }, {
                        path: "roles",
                        component: UserActivitiesComponent,
                        data: {
                            shouldDetach: true, mainRole: 'user',
                            subRole: 'edituser'
                        }
                    }
                ]
            }, {
                path: "user/details/basic/:id",

                component: UserBasicComponent, data: {
                    shouldDetach: true, mainRole: 'user',
                    subRole: 'edituser'
                }
            }, {
                path: "user/details/photo/:id",
                component: UserPhotoComponent, data: {
                    shouldDetach: true, mainRole: 'user',
                    subRole: 'edituser'
                }
            }, {
                path: "event",
                component: EventsListComponent,
                data: {
                    mainRole: 'ticket',
                    subRole: null
                }
            }, {
                path: "event/create",
                component: EventsCreateComponent,
                data: {
                    mainRole: 'ticket',
                    subRole: null
                }
            },
            {
                path: "event/list/update/:id",
                component: EventsCreateComponent,
                data: {
                    mainRole: 'ticket',
                    subRole: null
                }
            }, {
                path: "leavetype",
                component: LeaveTypeComponent,
                data: {
                    mainRole: 'ticket',
                    subRole: null
                }
            }, {
                path: "leavetype/create",
                component: LeaveTypeCreateComponent,
                data: {
                    mainRole: 'ticket',
                    subRole: null
                }
            },
            {
                path: "leavetype/update/:id",
                component: LeaveTypeCreateComponent,
                data: {
                    mainRole: 'ticket',
                    subRole: null
                }
            },
            {
                path: "company/setting",
                component: CompanySettingComponent,
                data: {
                    mainRole: 'ticket',
                    subRole: null
                }
            },
            /**
             * {
                                    path: "events",
                                    component: EventsHomeComponent,
                                    data: {
                                        mainRole: 'group',
                                        subRole: null
                                    },
                                    children:[
                                         { path: "", redirectTo: "list", pathMatch: "full",data: {
                                                mainRole: 'ticket',
                                                subRole: null
                                            } 
                                        },
                                        {
                                            path: "list",
                                            component: EventsListComponent,
                                            data: {
                                                mainRole: 'ticket',
                                                subRole: null
                                            }
                                        },{
                                            path: "create",
                                            component: EventsCreateComponent,
                                            data: {
                                                mainRole: 'ticket',
                                                subRole: null
                                            }
                                        },
                                    ]
            
                                }
             */




            {
                path: "user/details/link/:id",
                component: UserLinkComponent, data: {
                    shouldDetach: true, mainRole: 'user',
                    subRole: 'edituser'
                }
            }, {
                path: "user/details/mapuseraction/:id",
                component: UserMapActionComponent, data: {
                    shouldDetach: true, mainRole: 'user',
                    subRole: 'edituser'
                }
            },

            {
                path: "user/details/setting/:id",
                component: UserSettingComponent, data: {
                    shouldDetach: true, mainRole: 'user',
                    subRole: 'edituser'
                }
            },
            {
                path: "user/details/credential/:id",
                component: UserCredentialComponent, data: {
                    shouldDetach: true, mainRole: 'user',
                    subRole: 'edituser'
                }
            }, {
                path: "user/details/roles/:id",
                component: UserActivitiesComponent,
                data: {
                    shouldDetach: true, mainRole: 'user',
                    subRole: 'edituser'
                }
            }

        ]
    }


];
/*
export const routeConfig = [
   
    {
        path:"login",
        component:LoginPageComponent
    },

    {
        path: "home"
        , component: HomeComponent
        , canActivate: [CanActivateRouteGuard]

        , children: [
            {
                path: "",
                canActivateChild: [ActivityGuard]
                , children: [
                     { path: "", redirectTo: "feed", pathMatch: "full",data: {
                            mainRole: 'feed',
                            subRole: null
                        } },
                    {
                        
                        path:"users",
                        component:UsersComponent,
                        data: {
                            mainRole: 'feed',
                            subRole: null
                        }
                    },

                    {
                        path: "feed",

                        component: FeedsComponent, data: {
                            mainRole: 'feed',
                            subRole: null
                        }
                    },{
                        path:"form/:name",
                        component:FormViewComponent,data: {
                            mainRole: 'feed',
                            subRole: null
                        }
                    },
                    {
                        path: "noaccess",
                        component: AccessRestrictionComponent,
                        data: {
                            mainRole: 'noaccess'
                        }
                    }, {
                        path: "messages",
                        component: MessagesComponent, data: {
                            mainRole: 'message',
                            subRole: null
                        }
                    }, {
                        path: "groups",
                        component: GroupsComponent,
                        data: {
                            mainRole: 'group',
                            subRole: null
                        }

                    } ,{
                        path: "project",
                        component: ProjectHomeComponent,
                        data: {
                            mainRole: 'group',
                            subRole: null
                        },
                        children:[
                            {
                                path: "create",
                                component: ProjectCreateComponent,
                                data: {
                                    mainRole: 'ticket',
                                    subRole: null
                                }
                            },{
                                path: "edit/:id",
                                component: ProjectCreateComponent,
                                data: {
                                    mainRole: 'ticket',
                                    subRole: null
                                }
                            },{
                                path: "list",
                                component: ProjectListComponent,
                                data: {
                                    mainRole: 'ticket',
                                    subRole: null
                                }
                            }
                        ]

                    },{
                        path: "events",
                        component: EventsHomeComponent,
                        data: {
                            mainRole: 'group',
                            subRole: null
                        },
                        children:[
                             { path: "", redirectTo: "list", pathMatch: "full",data: {
                                    mainRole: 'ticket',
                                    subRole: null
                                } 
                            },
                            {
                                path: "list",
                                component: EventsListComponent,
                                data: {
                                    mainRole: 'ticket',
                                    subRole: null
                                }
                            },{
                                path: "create",
                                component: EventsCreateComponent,
                                data: {
                                    mainRole: 'ticket',
                                    subRole: null
                                }
                            },
                        ]

                    },
                    {
                        path: "newgroup",
                        component: NewgroupComponent,
                        data: {
                            mainRole: 'group',
                            subRole: 'creategroup'
                        }
                    }
                    , {
                        path: "albums",

                        component: PhotoalbumsComponent,
                        data: {
                            mainRole: 'album',
                            subRole: null
                        }

                    }, {
                        path: "newalbum",

                        component: PhotoalbumComponent
                        , data: {
                            mainRole: 'album',
                            subRole: 'createalbum'
                        }

                    }, {
                        path: "tickets",
                        component: TicketHomeComponent,
                        data: {
                            mainRole: 'ticket',
                            subRole: null
                        },
                        children: [
                            { path: "", redirectTo: "list", pathMatch: "full" },
                            {
                                path: "list",
                                component: TicketListComponent,
                                data: {
                                    mainRole: 'ticket',
                                    subRole: null
                                }
                            }, {
                                path: "create",
                                component: TicketCreateComponent,
                                data: {
                                    mainRole: 'ticket',
                                    subRole: 'createticket'
                                }
                            }, {
                                path: "edit/:id",
                                component: TicketCreateComponent,
                                data: {
                                    mainRole: 'editticket',
                                    subRole: null
                                }
                            }, {
                                path: "update/ticket/:ticketId",
                                component: TicketAssignComponent,
                                data: {
                                    mainRole: 'ticket',
                                    subRole: 'assignticket'
                                }
                            }
                        ]
                    },
                    {
                        path: "albums/photos/:id",
                        component: PhotosComponent,
                        data: {
                            mainRole: 'album',
                            subRole: 'uploadphotos'
                        }
                    },

                    {
                        path: "notification",
                        component: NotificationComponent,
                        children: [
                            {
                                path: "",
                                component: ReqNotifyComponent
                            },
                            {
                                path: "request",
                                component: ReqNotifyComponent
                            },
                            {
                                path: "like",
                                component: LikeNotifyComponent
                            }
                        ]
                    }

                ]
            }
        ]



    },
    {
        path: "admin",

        component: AdminComponent,
        canActivateChild: [ActivityGuard],
        children: [

            {
                path: "",
                component: CompanyComponent,
                data: {
                    mainRole: 'admin',
                    subRole: 'adminaccess'
                }
            },
            {

                path: "company",
                component: SelectedCompanyComponent,
                canActivateChild: [ActivityGuard],
                children: [
                    { path: "", redirectTo: "user", pathMatch: "full" },

                    {
                        path: "user",
                        component: UsersComponent,
                        data: {
                            mainRole: 'user',
                            subRole: 'viewuser'
                        }
                    },
                    {
                        path: "user/:id",
                        component: NewUserComponent,
                        data: {
                            mainRole: 'user',
                            subRole: 'edituser'
                        }
                    },
                    {
                        path: "usernew",
                        component: NewUserComponent,
                        data: {
                            mainRole: 'user',
                            subRole: 'createuser'
                        }
                    }
                ]
            }
        ]
    }, {
        path: "user/:userName",
        component: ProfileComponent,
        children: [
            //  { path: "", redirectTo: "address", pathMatch: "full" },
            {
                path: "address",
                component: AddressComponent
            }, {
                path: "professional",
                component: ProfessionComponent
            }, {
                path: "eduction",
                component: EducationComponent
            }
        ]
    }, {

        path: "admin/ticketcategory",
        component: TicketCategoryHomeComponent,
        children: [
            { path: "", redirectTo: "list", pathMatch: "full" },
            {
                path: "list",
                component: TicketCategoryListComponent

            }, {
                path: "create",
                component: TicketCategoryCreateComponent

            }, {
                path: "edit/:id",
                component: TicketCategoryCreateComponent

            }
        ]
    }, {
        path: "admin/setting",
        component: AdminSettingComponent,
        canActivateChild: [ActivityGuard],
        children: [
            { path: "", redirectTo: "organization", pathMatch: "full",
        data: {
                    mainRole: 'organization',
                    subRole: 'vieworganization',
                } },
            {
                path: "noaccess",
                component: AccessRestrictionComponent,
                data: {
                    mainRole: "noaccess"
                }
            },
            {
                path: "department",
                component: DepartmentComponent,
                canActivateChild: [ActivityGuard],
                data: {
                    mainRole: 'department',
                    subRole: 'viewdepartment',
                },
                children: [
                    {
                        path: "create",
                        component: DepartmentCreateComponent,
                        data: {
                            mainRole: 'department',
                            subRole: 'createdepartment',

                        }
                    },

                    {
                        path: "update/:id",
                        component: DepartmentCreateComponent,
                        data: {
                            mainRole: 'department',
                            subRole: 'editdepartment'
                        }
                    }
                ],

            }, {
                path: "designation",
                component: DesignationComponent,
                canActivateChild: [ActivityGuard],
                data: {
                    mainRole: 'designation',
                    subRole: 'viewdesignation',
                },
                children: [
                    {
                        path: "create",
                        component: DesignationCreateComponent,
                        data: {
                            mainRole: 'designation',
                            subRole: 'createdesignation'
                        }
                    },
                    {
                        path: "update/:id",
                        component: DesignationCreateComponent,
                        data: {
                            mainRole: 'designation',
                            subRole: 'editdesignation'
                        }
                    }
                ]
            }, {
                path: "team",
                component: TeamComponent,
                canActivateChild: [ActivityGuard],
                data: {
                    mainRole: 'team',
                    subRole: 'viewteam',
                },
                children: [
                    {
                        path: "create",
                        component: TeamCreateComponent,
                        data: {
                            mainRole: 'team',
                            subRole: 'createteam'
                        }
                    },
                    {
                        path: "update/:id",
                        component: TeamCreateComponent,
                        data: {
                            mainRole: 'team',
                            subRole: 'editteam'
                        }
                    }
                ]
            },{
                path: "lov",
                component: LovHomeComponent,
                canActivateChild: [ActivityGuard],
                data: {
                    mainRole: 'team',
                    subRole: 'viewteam',
                },
                children: [
                    {
                        path: "create",
                        component: LovCreateComponent,
                        data: {
                            mainRole: 'team',
                            subRole: 'createteam'
                        }
                    },{
                        path: "list",
                        component: LovListComponent,
                        data: {
                            mainRole: 'team',
                            subRole: 'createteam'
                        }
                    },
                    {
                        path: "update/:id",
                        component: LovCreateComponent,
                        data: {
                            mainRole: 'team',
                            subRole: 'editteam'
                        }
                    }
                ]
            }, {
                path: "organization",
                canActivateChild: [ActivityGuard],
                data: {
                    mainRole: 'organization',
                    subRole: 'vieworganization',
                },
                component: OrganizationComponent,
                children: [
                    {
                        path: "create",
                        component: OrganizationCreateComponent,
                        data: {
                            mainRole: 'organization',
                            subRole: 'createorganization',
                        }
                    },
                    {
                        path: "update/:id",
                        component: OrganizationCreateComponent,
                        data: {
                            mainRole: 'organization',
                            subRole: 'editorganization',
                        }
                    },
                    {
                        path: "child/:id",
                        component: OrganizationCreateComponent,
                        data: {
                            mainRole: 'organization',
                            subRole: 'createorganization',
                        }
                    }
                ]
            }, {
                path: "user",
                component: UserListComponent,
                data: {
                    mainRole: 'user',
                    subRole: 'viewuser',
                }
            }, {
                path: "user/create",
                component: UserCreateComponent,
                data: {
                    mainRole: 'user',
                    subRole: 'createuser',
                }

            }, {
                path: "user/details/:id",
                component: UserDetailsComponent,
                canActivateChild: [ActivityGuard],
                data: {
                    mainRole: 'user',
                    subRole: 'edituser',
                },
                children: [
                    {
                        path: "", redirectTo: "edit", pathMatch: "full",data: {
                            shouldDetach: true, mainRole: 'user',
                            subRole: 'edituser'
                        }
                    },
                    {
                        path: "edit",

                        component: UserBasicComponent, data: {
                            shouldDetach: true, mainRole: 'user',
                            subRole: 'edituser'
                        }
                    },
                    {
                        path: "photo",

                        component: UserPhotoComponent, data: {
                            shouldDetach: true, mainRole: 'user',
                            subRole: 'edituser'
                        }
                    },
                    {
                        path: "link",
                        component: UserLinkComponent, data: {
                            shouldDetach: true, mainRole: 'user',
                            subRole: 'edituser'
                        }
                    },
                    {
                        path: "setting",
                        component: UserSettingComponent, data: {
                            shouldDetach: true, mainRole: 'user',
                            subRole: 'edituser'
                        }
                    },
                    {
                        path: "credential",
                        component: UserCredentialComponent, data: {
                            shouldDetach: true, mainRole: 'user',
                            subRole: 'edituser'
                        }
                    }, {
                        path:"activities",
                    component:UserActivitiesComponent,
                    data: {
                            shouldDetach: true, mainRole: 'user',
                            subRole: 'edituser'
                        }
                    }
                ]
            }

        ]
    }, {
        path: "group/:name",
        component: GroupLeftComponent,
        children: [
            { path: "", redirectTo: "feed", pathMatch: "full" },
            {
                path: "feed",

                component: GroupMiddleComponent
                , pathMatch: "full"
                //  ,outlet: 'groupMain'
            },
            {
                path: "members",
                component: GroupusersComponent

                //           outlet: 'groupMain'
            }
        ]
    }

];*/