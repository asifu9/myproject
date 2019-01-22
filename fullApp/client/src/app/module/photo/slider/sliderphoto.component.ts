import { Component, OnInit, Input, AfterViewInit ,ViewChild} from '@angular/core';
import { UserslideComponent } from "../../photo/userslide/userslide.component";
import { SessionService } from "../../shared/shared/SessionService";
import { Photo, User } from "../../shared/shared/models";
import { HttpService } from "../../shared/shared/httpService";
declare var jQuery: any;


@Component({
  selector: 'sliderphoto',
  templateUrl: './sliderphoto.component.html',
  styleUrls: ['./sliderphoto.component.scss'],
  providers:[UserslideComponent]
})
export class SliderPhotoComponent implements OnInit, AfterViewInit {

  constructor(private sessionService: SessionService, private apiService: HttpService) { }
  @Input()
  photoId: string;
  @Input()
  post: any;
  @Input()
  parent: any;
  photos: Photo[] = new Array<Photo>();
  currentPhoto: Photo;
  showDiv: boolean = false;
  user: User;
  index:number=0;
  increaseHeight: boolean = false;
  @ViewChild(UserslideComponent) child;
  ngAfterViewInit() {
    console.log("after init");
  }
 
  ngOnInit() {
    if (this.post != null && this.post.length > 0) {
      this.post.forEach(i => {
        if (i.id == this.photoId) {
          if (i.createdByObj == null) {
              i.createdByObj=this.fetchUser(i.createdBy);
          } else {
            this.user = i.createdByObj;
          }
        }
      });
    }

    this.showPhoto();
    let photoFirst:Photo;
    let arr=new Array();
    let albumId='';
    if (this.post != null && this.post.length > 0) {
      this.post.forEach(i => {
        arr.push(i.id);
        albumId=i.albumId;

      });
     this.loadOtherPhotos(albumId,arr);
  }
  }
  fetchUser(userId) {
    console.log("ffsdss");
    this.apiService.getData("/User/" + userId).subscribe((data) => {
      return data;
    });
  }
  toggleDiv() {
    if (this.showDiv == true) {
      jQuery("#infoSection").show();
      this.showDiv = false;
    } else {
      jQuery("#infoSection").hide();
      this.showDiv = true;
    }
  }
  showPhoto() {
    let photoFirst: Photo = null;
    let arr=new Array();
    let albumId='';
    if (this.post != null && this.post.length > 0) {
      this.post.forEach(i => {
        arr.push(i.id);
        if (i.id == this.photoId) {
          //store it in an variable
          photoFirst = this.sessionService.updatePhotolarge(i);
        } else {
          this.photos.push(this.sessionService.updatePhotolarge(i));
        }
        albumId=i.albumId;

      });
      this.photos.unshift(photoFirst);
      this.currentPhoto = photoFirst;
      this.adjustImgage();
      this.hideArrows();
this.index=0;
    }
  }
  lastPhoto(){
       this.index=this.index-1;
       if(this.index<0){
         this.index=this.photos.length-1;
       }
    this.currentPhoto=this.photos[this.index];
    this.photoId=this.currentPhoto.id;
    this.user=this.currentPhoto.createdByObj;
    this.currentPhoto=this.sessionService.updatePhotolarge(this.currentPhoto);
    // if(this.currentPhoto.createdByObj!=null){
    //     this.currentPhoto.createdByObj.photo=this.sessionService.updatePhotoIcon(this.currentPhoto.createdByObj.photo);
    // }
    this.child.fetchComments(this.photoId);
   this.adjustImgage();
   this.hideArrows();
  }
  nextPhoto(){
    this.index=this.index+1;
    if((this.index>=this.photos.length-1)){
      this.index=0;
    }
    this.currentPhoto=this.photos[this.index];
    this.currentPhoto=this.sessionService.updatePhotolarge(this.currentPhoto);
    this.photoId=this.currentPhoto.id;
    this.user=this.currentPhoto.createdByObj;
    // if(this.currentPhoto.createdByObj!=null){
    //     this.currentPhoto.createdByObj.photo=this.sessionService.updatePhotoIcon(this.currentPhoto.createdByObj.photo);
    // }
    this.child.fetchComments(this.photoId);
    this.adjustImgage();
    this.hideArrows();
  }
  loadOtherPhotos(albumId,array){
      this.apiService.getData( "/Albums/filter/" + albumId).subscribe((data) => {
          if(data!=null && data.length>0){
              data.forEach(e => {
                if(array.indexOf(e.id)==-1){
                    this.photos.push(e);
                }
              });
          }

    });
  }
  hideArrows(){
    jQuery("#leftImg").hide();
    jQuery("#rightImg").hide();
  }
   showArrows(){
      jQuery("#leftImg").show();
    jQuery("#rightImg").show();
     setTimeout(function(){
        jQuery("#leftImg").hide();
         jQuery("#rightImg").hide();
     },10000);
   
  }

  onHoveCell(){
    let imgObj= document.getElementById("mainImg");
    let height= jQuery("#mainImg").prop('naturalHeight');
    //let height=imgObj.naturalHeight;
    let width=jQuery("#mainImg").prop('naturalWidth');
    if(height!=0){
      jQuery("#leftImg").css("marginTop",""+height/2+"px");
    }
    if(this.increaseHeight==true)
      {
        //need to handle
        jQuery("#leftImg").css("marginLeft","-10px");
        jQuery("#leftImg").css("position","absolute");
      } else{   
        jQuery("#leftImg").css("position","absolute");
      }

      if(height!=0){
        jQuery("#rightImg").css("marginTop",""+height/2+"px");
      }
      jQuery("#rightImg").css("marginLeft",width-130+"px");
      
       jQuery("#rightImg").css("position","absolute");
this.showArrows();
  }

  adjustImgage() {
     this.increaseHeight=false;
    if (this.currentPhoto != null && this.currentPhoto.details != null && this.currentPhoto.details.length > 0) {
      console.log(this.currentPhoto.details);
      if (this.currentPhoto.details.length == 1) {
        if (this.currentPhoto.details[0].height < 300) {
          // increase ui height
          this.increaseHeight = true;
          // jQuery("#mainImg").css("paddingTop","200px");
          // jQuery("#mainImg").css("paddingBottom","200px");
          //  jQuery("#imgSection").css("height","400px");
          jQuery("#mainImg").css("marginLeft", "auto");
          jQuery("#mainImg").css("marginRight", "auto");
        }
      } else if (this.currentPhoto.details.length == 2) {
        if (this.currentPhoto.details[1].height < 300) {
          // increase ui height
          this.increaseHeight = true;
          // jQuery("#mainImg").css("paddingTop","200px");
          // jQuery("#mainImg").css("paddingBottom","200px");
          // jQuery("#imgSection").css("height","400px");
          jQuery("#mainImg").css("marginLeft", "auto");
          jQuery("#mainImg").css("marginRight", "auto");
        }
      } else if (this.currentPhoto.details.length == 3) {
        if (this.currentPhoto.details[2].height < 300) {
          // increase ui height
          this.increaseHeight = true;
          // jQuery("#mainImg").css("paddingTop","200px");
          // jQuery("#mainImg").css("paddingBottom","200px");
          //  jQuery("#imgSection").css("height","400px");
          jQuery("#mainImg").css("marginLeft", "auto");
          jQuery("#mainImg").css("marginRight", "auto");
        }
      } else if (this.currentPhoto.details.length == 4) {
        if (this.currentPhoto.details[3].height < 300) {
          // increase ui height
          this.increaseHeight = true;
          // jQuery("#mainImg").css("paddingTop","200px");
          // jQuery("#mainImg").css("paddingBottom","200px");
          //    jQuery("#imgSection").css("height","400px");
          jQuery("#mainImg").css("marginLeft", "auto");
          jQuery("#mainImg").css("marginRight", "auto");
        }
      } else if (this.currentPhoto.details.length == 5) {
        if (this.currentPhoto.details[3].height < 300) {
          // increase ui height
          this.increaseHeight = true;
          //  jQuery("#mainImg").css("paddingBottom","200px");
          // jQuery("#mainImg").css("paddingTop","200px");
          //  jQuery("#imgSection").css("height","400px");
          jQuery("#mainImg").css("marginLeft", "auto");
          jQuery("#mainImg").css("marginRight", "auto");
        }
      } else if (this.currentPhoto.details.length == 6) {
        if (this.currentPhoto.details[3].height < 300) {
          // increase ui height
          this.increaseHeight = true;
          // jQuery("#mainImg").css("height","400px");
          //jQuery("#mainImg").css("paddingTop","200px");
          //  jQuery("#imgSection").css("height","400px");
          jQuery("#mainImg").css("marginLeft", "auto");
          jQuery("#mainImg").css("marginRight", "auto");
        }
      }
    }
    this.onHoveCell();
  }


}
