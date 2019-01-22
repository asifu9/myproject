
export interface IData {

}

export class User implements IData {


    id: string;
    name: string;
    dob: number;
    primaryContact: string;
    secondaryContact: string;
    email: string;
    photo: Photo;
    photoId: string;
    photoPath: string;
    tenantId: string;
    emailId: string;
    doj: number;

}
export class Notification {
    id: string;
    userId: string;
    companyId: string;
    type: string;
    url: string;
    valueId: string;
    valueObject: any;
    createdOn: number;
    updatedOn: number;
    status: number;
    details: string;
    sourceId: string;
    destinationId: string;
}
export class RoleModel implements IData {
    name: string;
    roleId: string;
    constructor(name,roleId) {
        this.name = name;
        this.roleId = roleId;
    }
}
export class Role implements IData {
    id: string;
    name: string;
    type: string;
    tenantId: string;
    createdBy: string;
    updatedOn: number;
    createdOn: number;
    isActive: number;
    roleId:string;
    
    constructor(name?,roleId?){
        this.name=name;
        this.roleId=roleId;
    }
}
export class DropdownValue implements IData {
    value: string;
    label: string;
    obj: any;
    type: string;

    constructor(value: string, label: string, obj: any, type: string) {
        this.value = value;
        this.label = label;
        this.obj = obj;
        this.type = type;
    }
}

export class TicketCategory implements IData {
    id: string;
    name: string;
    createdOn: number;
    updatedOn: number;
    isActive: boolean;
    tenantId: string;
    createdBy: string;
}
export class PriorityList {
    priority: number;
    name: string;
    constructor(p: number, name: string) {
        this.priority = p; this.name = name;
    }
}
export class Ticket implements IData {
    id: string;
    subject: string;
    description: string;
    ticketCategoryName: string;
    ticketCategoryId: string;
    assignedTo: string;
    status: string;
    notifyToManager: boolean;
    attachments: any;
    priority: string;
    notifyOthers: any;
    updates: TicketUpdate[];
    createdOn: number;
    updatedOn: number;
    isActive: boolean;
    tenantId: string;
    createdBy: string;
    updatedBy: string;
    createdByUser: UserJson1;
}
export class Activities {
    id: string;
    activities: any;
    tenantId: string;
    createdBy: string;
    createdOn: number;
    updatedBy: string;
    updatedOn: number;
    isActive: boolean;
}
export class AcitivityMap {
    name: string;
    items: any;
}

export class ConfigValues {
    id: string;
    storedValue: string;
    displayValue: string;
    type: string;
    locale: string;
    createdOn: number;
    updatedOn: number;
    isActive: boolean;
    attachments: any;
    tenantId: string;

}

export class TicketUpdate {
    updatedBy: string;
    updatedByUser: UserJson1;
    tenantId: string;
    description: string;
    updatedOn: number;
    createdOn: number;
    isActive: boolean;
}
export class MapUserAction {
    id: string;
    userId: string;
    user: User;
    tenantId: string;
    type: string;
    typeValue: string;
    typeImagePath: string;
    createdBy: string;
    updatedBy: string;
    isActive: boolean;
}
export class Events {
    id: string;
    tenantId: string;
    name: string;
    description: string;
    createdOn: number;
    eventStartDate: number;
    eventEndDate: number;
    updatedOn: number;
    photoId: string[];
    photoIdData: Photo[];
    videoId: string[];
    likeCount: number;
    commentCount: number;
    createdBy: string;
    updatedBy: string;
    createdByUser: User;
    updatedByUser: User;
    status: number;
}
export class Reminders {
    id: string;
    tenantId: string;
    createdBy: string;
    updatedBy: string;
    createdOn: number;
    updatedOn: string;
    type: string;
    typeValue: string;
    dates: number[];
    notificationTypes: string[];
}

export class LeaveType {
    id: string;
    companyId: string;
    name: string;
    count: number;
    description: string;
    carryFarward: boolean;
    carryFarwardLimit: number;
    createdOn: number;
    updatedOn: number;
    assingType: string;
    gender: string;
    financialYear: string;
}
export class CompanySetting {
    id: string;
    dateFormat: string;
    leaveStartEndMonths: string;
    createdOn: number;
    adminMail: string;
    currencyFormat:string;
    currencySymbol:string;
    timeZone:string;
    updatedOn: number;
    lastUpdatedBy: string;
    lastUpdatedByUser: User;
    featureList:string[];
}
export class UserLeaves {

    id: string;
    companyId: string;
    userId: string;
    isCurrent: boolean;
    year: number;
    createdOn: number;
    updatedOn: number;
    financialYear: string;
    leaves: LeaveDetails[];
}
export class LeaveApply {
    id: string;
    leaveName: string;
    companyId: string;
    userId: string;
    days: number;
    startDate: number;
    halfDay: boolean;
    endDate: number;
    status: string;
    createdOn: number;
    description: string;
    updatedOn: number;
    assignedTo: string;
    financialYear: string;
    comments: LeaveApplyComment[];
}
export class LeaveApplyComment {
    createdOn: number;
    description: string;
    commentedBy: string;
}
export class ReportMeta {
    label: string;
    key: string;
    list: any[];
    type: string;
    constructor(_label: string, _key, list?: any[], type?: string) {
        this.label = _label;
        this.key = _key;
        this.list = list;
        this.type = type;
    }

}
export class LeaveDetails {
    index: number;
    leaveName: string;
    count: number;
    takenLeaves: number;
    hiddenCarrayFowardCount: number;
    accumulatedLeaves: number;
}

export class UserJson1 {
    id: string;
    name: string;
    photoPath: string;
    userName: string;
    emailId: string;
}
export class ReportConfig implements IData {
    id: string;
    tenantId: string;
    reportId: string;
    columns: Column[];
    createdOn: number;
    updatedOn: number;
    createdBy: string;
    updatedBy: string;
}
export class Form {
    id: string;
    name: string;
    description: string;
    isTestForm: boolean;
    lowMode: number;
    tenantId: string;
    formName: string;
    createdBy: string;
    updatedBy: string;
    createdOn: number;
    updatedOn: number;
    createAPIUrl: string;
    updateAPIUrl: string;
    deleteAPIUrl: string;
    metaData: WidgetMetaData[] = [];
    collectionName: string;
}
export class Options {
    index: number;
    storedValue: string;
    displayValue: string;
    locale: string;
}
export class WidgetMetaData {
    index: number;
    hidden: boolean;
    widgetId: string;
    widgetName: string;
    options: Options[];
    widgetDisplayName: string;
    widgetType: string;
    widgetDataType: string;
    dateFormat: string;
    expression: string;
    minValue: number;
    maxValue: number;
    required: boolean;
    listApiName: string;
    multiple: boolean;
    listApiStoredValueColumn: string;
    listApiDisplayValueColumn: string;
    placeholderValue: string;
    defaultValue: string;
    inputFormat: string;
    items: WidgetMetaData[];
    flowMode: any;
}

export class Column {
    index: number;
    columnName: string;
    columnDisplayName: string;
    columnType: string;
    filterType: string;
    editLink: boolean;
    dateFormat: string;
    hidden: boolean;
    isClicked: boolean;

}
export class FormAttachment {

    id: string;
    tenantId: string;
    form: string;
    createdBy: string;
    list: FormAttachmentItems[];
    createdOn: number;
    formId: string;
}
export class FormAttachmentItems {
    attId: string;
    fileName: string;
    path: string;
    createdOn: number;
}
export class PhotoAlbum implements IData {
    id: string;
    name: string;
    description: string;
    privacyStatus: number;
    createdBy: string;
    updatedOn: number;
    createdOn: number;
    photoId: string;
    likeCount: number;
    commentCount: number;
    photo: Photo;
    photoPath: string;

}
export class Photo implements IData {
    id: string;
    description: string;
    path: string;
    createdOn: number;
    privacyStatus: number;
    toggleStatus: number;
    albumId: string;
    createdBy: string;
    createdByObj: User;
    updatedOn: number;
    likes: PhotoLikes;
    details: PhotoDetails[];
    displayPath: string;
}
export class PhotoDetails implements IData {
    width: number;
    height: number;
    order: number;
    name: string;
}
export class PhotoLikes implements IData {
    id: string;
    tenantId: number;
    likedBy: object;
    createdOn: number;
    count: number;
}
export class MyMap implements IData {
    key: string;
    value: boolean;
}
export class Features implements IData {
    id: string;
    name: string;
    createdBy: string;
    updatedBy: string;
    updatedOn: number;
    createdOn: number;
    description: string;
}
export class Feed implements IData {
    id: string;
    tenantId: number;
    createdBy: string;
    createdByObj: User;
    createdOn: number;
    createdOnStr: string;
    likeCountObj: FeedLikes;
    likeCount: number;
    commentCountObj: FeedComments;
    commentCount: number;
    feedStatus: string;
    description: string;
    feedType: string;
    objectId: any;
    objectIdObj: any;
    originalPostId: string;
    originalPostIdObj: any;
    photo: Photo;
    wallId: string;
    shareCount: number;
    feedSetting: any;
}



export class FeedLikes implements IData {
    id: string;
    tenantId: number;
    likedBy: object;
    createdOn: number;
    count: number;
}
export class FeedComments implements IData {
    id: string;
    tenantId: number;
    comments: CBlock[];
    count: number;

}
export class CBlock implements IData {
    id: string;
    userId: string;
    comment: string;
    createdOn: number;
    createdOnStr: string;
    likedBy: object;
    likeCount: number;
}

export class PhotoComments implements IData {
    id: string;
    tenantId: number;
    comments: CBlock[];
    count: number;

}

export class UploadFile implements IData {
    file: string;
}
export class Address implements IData {
    country: string;
    state: string;
    city: string;
    street: string;
    landMark: string;
    pincode: string;
    addressType: string;
    userId: string;
    tenantId: string;
    id: String;
}
export class FriendRequest implements IData {
    id: string;
    requestedFrom: string;
    requestedTo: string;
    createdOn: number;
    acceptedOn: number;
    rejectedOn: number;
    status: string;
    tenantId: string;
    initialMessage: string;
    active: boolean;
}
export class Messages implements IData {
    id: string;
    tenantId: string;
    messagedBy: string;
    groupId: string;
    messagedTo: string;
    createdOn: number;
    message: string;
    status: number;
    messageToUser: User;
    messagedByUser: User;
    likeCount: number;
    commentCount: number;
    shareCount: number;
    comments: Messages[];

}
export class MessageChannel implements IData {
    id: string;
    name: string;
    description:string;
    tenantId: string;
    createdBy: string;
    createdByUser: User;
    createdOn: number;
    updatedOn: number;
    updatedBy: string;
    photoId:string;
    photo:Photo;
    members: string[];

}

export class Items implements IData {
    createdBy: string;
    createdByUser: User;
    createdOn: number;
    id: string;
    joinPerReq: boolean;
    members: string[];
    name: string;
    tenantId: string;
    updatedOn: number;
}
export class UserMapItems implements IData {

    id: string;
    tenantId: string;
    messageUser: string[] = [];
    messageUserObj: User[] = [];
    messageChannel: string[] = [];
    messageChannelObj: MessageChannel[] = [];
    messageUserCounter: Map<string, number> = new Map<string, number>();
    messageChannelCounter: Map<string, number> = new Map<string, number>();
}
export class MessageGroup implements IData {
    id: string;
    tenantId: string;
    message: any;

}
export class Group implements IData {
    id: string;
    tenantId: string;
    profilePhotoId: string;
    name: string;
    createdOn: number;
    createdOnStr: string;
    updatedOn: number;
    photos: string[];
    privacyStatus: string;
    ShowHide: string;
    createdBy: string;
    likesCount: number;
    likes: GroupLikes;
    commentsCount: number;
    memberCount: number;
    description: string;
    profilePhoto: Photo;
    photoList: Photo[];
    createdByUser: User;
    groupUniqueName: string;
    members: string[];
    statusMembers: any;
    memberDetails: any;

}


export class GroupLikes implements IData {
    id: string;
    tenantId: number;
    likedBy: object;
    createdOn: number;
    count: number;
}

export class GroupMembers implements IData {
    id: string;
    groupId: string;
    userId: string;
    status: string;
    tenantId: string;
    requestedOn: number;
    acceptedOn: number;
    rejectedOn: number;
    statusDateStr: string;
    user: User;
}

export class Company implements IData {
    id: string;
    name: string;
    locationId: string;
    headQuater: boolean;
    createdBy: string;
    updatedOn: number;
    photoId: string;
    createdOn: number;
    uname: string;
    isActive: boolean;

}

export class UserLinkInfo implements IData {
    id: string;
    managerId: string;

    departmentId: string;
    designationId: string;
    teamId: string;
    tenantId: string;
    createdOn: number;
    updatedOn: number

    department: Department;
    manager: User;
    designation: Designation;
    team: Team;
}

export class Organization implements IData {
    id: string;
    tenantId: string;
    orgName: string;
    parentOrgId: string;
    locationId: string;
}
export class Designation implements IData {
    id: string;
    tenantId: string;
    designationName: string;
}
export class Department implements IData {
    id: string;
    tenantId: string;
    name: string;
}
export class Team implements IData {
    id: string;
    tenantId: string;
    name: string;
}