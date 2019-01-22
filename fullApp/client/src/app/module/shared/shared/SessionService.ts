
import { Injectable } from '@angular/core';
import { Photo, Activities, User, Company, CompanySetting } from '../shared/models';
import { HttpService } from "./httpService";

import { Observable, Subject, BehaviorSubject } from 'rxjs';
interface ShareObj {
  [id: string]: any;
}

@Injectable()
export class SessionService {

  public userSubject: Subject<any> =
  new Subject<any>();
  public userSubject$: Observable<any> = this
    .userSubject.asObservable();

    leftMenuSelected :BehaviorSubject<any>=new BehaviorSubject<any>(null);

  public dataLoaded: Subject<any> =
  new Subject<any>();
  public dataLoaded$: Observable<any> = this
    .userSubject.asObservable();
  currentUser: User;
  currentCompany: Company;
  currentSetting: CompanySetting;
  currentRoles=[];
  isUserLoggedIn: boolean = false;
  shareObj: ShareObj;
  users: Map<string, any> = new Map<string, any>();
  photos: Map<string, any> = new Map<string, any>();
  activities: Activities;
  isActivityFetch: boolean = false;
  isActivityInProgress: boolean = false;
  fetchActivitySubject = new Subject<any>();
  fetchActivitySubjectResult$ = this.fetchActivitySubject.asObservable();
  path: string = '/uploads';
  constructor(private httpService: HttpService) {
  }
  setCurrentUser(user) {
    localStorage.setItem('user', JSON.stringify(user));
    this.currentUser = user;
    this.shareObj['userObj'] = user;
    this.fetchActivities();
  }
  setCurrentDetails(user, company, token,roles,setting) {
    this.isUserLoggedIn = true;
    this.currentUser = user;
    this.currentCompany = company;
    this.currentRoles=roles;
    this.currentSetting=setting;

    localStorage.setItem('user', JSON.stringify(user));
    localStorage.setItem('company', JSON.stringify(company));
    localStorage.setItem('token', JSON.stringify(token));
    localStorage.setItem('roles',JSON.stringify(roles));
    localStorage.setItem('setting',JSON.stringify(setting));
    this.shareObj = {
      'url': 'http://localhost:6541/attachments', 'wall': this.currentCompany.id, 'user': user.id
    };
    this.shareObj['userObj'] = user;
    this.fetchActivities();

  }
  isUserAuthenticated() {
    if (!this.isUserLoggedIn) {
      if (localStorage.getItem('user')) {
        this.setCurrentDetails(JSON.parse(localStorage.getItem('user')), JSON.parse(localStorage.getItem('company')), JSON.parse(localStorage.getItem('token')),
          JSON.parse(localStorage.getItem('roles')),JSON.parse(localStorage.getItem('setting')));
      }
    }
    return this.isUserLoggedIn;
  }

  isLoggedIn() {
    return this.isUserLoggedIn;
  }

  fetchActivities() {

    this.isActivityInProgress = true;
    this.httpService.getData("/activities/" + this.shareObj['user']).subscribe(result => {
      this.activities = result;
      this.fetchActivitySubject.next(result);
      this.isActivityFetch = true;
      this.isActivityInProgress = false;
      this.dataLoaded.next(true);
    }, error => {
      this.dataLoaded.next(false);
    });
  }
  isActivityExists(type, name) {
    return true;
    // if(this.isActivityFetch){
    //   if(this.activities && this.activities.activities ){
    //      let obj=this.activities.activities[type];
    //      if(obj==null){
    //        return false;
    //      }else if(name && obj[name] && obj[name]=='true'){
    //        return true;
    //      }else if(obj && !name){
    //        return true;
    //      }else{
    //        return false;
    //      }

    //   }else{
    //     return true;
    //   }
    // }else{

    //   if(!this.isActivityInProgress){
    //     this.isActivityInProgress=true;
    //      this.httpService.getData("/activities/"+this.shareObj['user']).subscribe(result=>{
    //       this.activities=result;
    //       console.log("ok got activities in second");
    //       this.fetchActivitySubject.next(result);
    //       this.isActivityFetch=true;
    //       this.isActivityInProgress=false;
    //       this.isActivityExists(type,name);
    //     });
    //   }

    // }
  }

  updateMenu(item){
    this.leftMenuSelected.next(item);
  }

  updatePhoto(obj) {
    if (obj == null || obj == undefined) {

      obj = new Photo();
      obj.displayPath = "/assets/profileimages/img1.jpg";
    } else {
      if (obj.details.length >= 3) {
        obj.displayPath = this.path + '/' + obj.createdBy + '/' + obj.albumId + '/' + obj.details[2].name;
      } else if (obj.details.length >= 2) {
        obj.displayPath = this.path + '/' + obj.createdBy + '/' + obj.albumId + '/' + obj.details[1].name;
      } else {
        obj.displayPath = this.path + '/' + obj.createdBy + '/' + obj.albumId + '/' + obj.details[0].name;
      }
    }
    return obj;
  }
  updatePhotolarge(obj) {
    if (obj == null || obj == undefined) {

      obj = new Photo();
      obj.displayPath = "/assets/profileimages/img1.jpg";
    } else {
      if (obj.details.length == 0) {
        obj.displayPath = this.path + '/' + obj.createdBy + '/' + obj.albumId + '/' + obj.details[0].name;
      } else if (obj.details.length == 1) {
        obj.displayPath = this.path + '/' + obj.createdBy + '/' + obj.albumId + '/' + obj.details[obj.details.length - 1].name;

      } else {
        obj.displayPath = this.path + '/' + obj.createdBy + '/' + obj.albumId + '/' + obj.details[obj.details.length - 2].name;
      }


    }
    return obj;
  }

  showPhoto(obj){
    if(obj){
     return "http://localhost:6541/attachments" + this.path + '/' + obj.createdBy + '/' + obj.albumId + '/' + obj.details[0].name;
    }else{
      return "http://localhost:6541/attachments/profileimages/img1.jpg";
    }
  }
    showPhotoTest(obj){
    console.log(obj);
    if(obj){
     return "http://localhost:6541/attachments" + this.path + '/' + obj.createdBy + '/' + obj.albumId + '/' + obj.details[0].name;
    }else{
      return "http://localhost:6541/attachments/profileimages/img1.jpg";
    }
  }


 defaultPhotoAlbum:string="http://localhost:6541/attachments/profileimages/img1.jpg";
  updatePhotoIcon(obj, type) {
    let ppppath = "";
    if (obj == null || obj == undefined) {
      ppppath = "http://localhost:6541/attachments/profileimages/img1.jpg";
    } else if (type === 'user') {
      console.log("before chedckihg users " + obj.id);
      console.log(this.users);
      if (this.users) {
        let tttt = this.users[obj.id];
        if (tttt) {
         
          if (tttt.photoId && this.photos[tttt.photoId]) {
            let temp = this.photos[tttt.photoId];
            ppppath = "http://localhost:6541/attachments" + this.path + '/' + temp.createdBy + '/' + temp.albumId + '/' + temp.details[0].name;
          }else{
            console.log("photo nont found in map")
          }
        }else{
          console.log("user oject not found in map")
        }

      }else{
        console.log("user not found");
      }
    } else if (type === 'photo') {

      let photoTemp = this.photos[obj.id];
      if (this.photos && photoTemp) {
        let temp = this.photos[obj.id];
        ppppath = "http://localhost:6541/attachments" + this.path + '/' + temp.createdBy + '/' + temp.albumId + '/' + temp.details[0].name;

      } else {
        ppppath = "http://localhost:6541/attachments/profileimages/img1.jpg";
      }
    }
    return ppppath;
  }
  inProgressCalls=[];;
  // updatePhotoIconTest(obj, type):Observable<string> {
  //    let sendResult = new Subject<string>();
  //   let ppppath = "";
  //   if (obj == null || obj == undefined) {
  //     ppppath= "http://localhost:6541/attachments/profileimages/img1.jpg";
  //      sendResult.next(ppppath);
  //   } else if (type === 'user') {
  //     if(obj && obj.photoId){
  //       let phototttttt=this.photos.get(obj.photoId)
  //         if (phototttttt) {
  //           ppppath= "http://localhost:6541/attachments" + this.path + '/' + phototttttt.createdBy + '/' + phototttttt.albumId + '/' + phototttttt.details[0].name;
  //           sendResult.next(ppppath);
  //         }else{
  //           console.log("photo id test1 " + obj.photoId);
  //           if(this.inProgressCalls.indexOf(obj.photoId)===-1){
  //              this.inProgressCalls.push(obj.photoId);
  //              this.httpService.getData("/Albums/photo/"+obj.photoId).subscribe(result=>{
  //                this.photos.set(obj.photoId,result);
  //                console.log(this.inProgressCalls)
  //                console.log("id is here with index "+this.inProgressCalls.indexOf(obj.photoId))
  //                this.inProgressCalls.slice(this.inProgressCalls.indexOf(obj.photoId),1);
  //                 ppppath= "http://localhost:6541/attachments" + this.path + '/' + result.createdBy + '/' + result.albumId + '/' + result.details[0].name;
  //                 sendResult.next(ppppath);
  //             })
  //           }else{
  //               console.log("already checking the id " + obj.photoId + " type " + type);
  //               setTimeout(()=>{
  //                 console.log("ok good one super cool for obj " + type);
  //                 return this.updatePhotoIconTest(obj, type);
  //               },1000);
                
  //           }
           
  //         }
  //     }else
  //           if (this.users) {
  //       let tttt = this.users.get(obj.id);
  //       if (!tttt) {
  //         console.log("user id test1 " + obj.photoId);
  //         this.httpService.getData("/User/" + obj.id).subscribe(result => {
  //           tttt = result;
  //           this.users.set(obj.id, tttt);
  //           if (tttt.photoId && this.photos.get(tttt.photoId)) {
  //             let temp = this.photos.get(tttt.photoId);
  //             ppppath= "http://localhost:6541/attachments" + this.path + '/' + temp.createdBy + '/' + temp.albumId + '/' + temp.details[0].name;
  //             sendResult.next(ppppath);
  //           }
  //         });
  //       } else {
  //         if (tttt.photoId && this.photos.get(tttt.photoId)) {
  //           let temp = this.photos.get(tttt.photoId);
  //           ppppath= "http://localhost:6541/attachments" + this.path + '/' + temp.createdBy + '/' + temp.albumId + '/' + temp.details[0].name;
  //           sendResult.next(ppppath);
  //         }
  //       }

  //     }
  //   } else if (type === 'photo') {

  //     let photoTemp = this.photos.get(obj.id);
  //     if (this.photos && photoTemp) {
  //       let temp = this.photos.get(obj.id);
  //       ppppath= "http://localhost:6541/attachments" + this.path + '/' + temp.createdBy + '/' + temp.albumId + '/' + temp.details[0].name;
  //       sendResult.next(ppppath);

  //     } else {
  //       ppppath= "http://localhost:6541/attachments/profileimages/img1.jpg";
  //       sendResult.next(ppppath);
  //     }
  //   }
  //   return sendResult.asObservable();;
  // }

  appendURL(u) {
    return "http://localhost:6541/attachments/" + u;
  }

}