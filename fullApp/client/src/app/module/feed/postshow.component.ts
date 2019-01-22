import { Component, OnInit, Input,ViewChild ,ViewContainerRef,ComponentFactoryResolver } from '@angular/core';
import { CommentsComponent } from "../comments/comments/comments.component";
import { PostInnerComponent } from "./postinner.component";
import { Feed } from "../shared/shared/models";
import { PopupSliderComponent } from "../shared/shared/popup/popupslider.component";
import { SliderComponent } from "../photo/slider/slider.component";
declare var jQuery: any;
@Component({
  selector: 'postshow',
  templateUrl: './postshow.component.html',
  styleUrls: ['./postshow.component.scss'],
  providers: [CommentsComponent,PostInnerComponent,SliderComponent]

})
export class PostShowComponent implements OnInit {
  @Input()
   post: Feed;
   @Input()
   imgSize:number;
  private path:string='/assets/uploads';
  photo1:string;
  photo2:string;
  photo3:string;
  photo4:string;
  photo5:string;
  photo6:string;
    photoid1:string;
  photoid2:string;
  photoid3:string;
  photoid4:string;
  photoid5:string;
  photoid6:string;
  displayType:number;

    copom:any;
@ViewChild("slider1") slider;
 @ViewChild(SliderComponent) sliderChild:SliderComponent;

  @ViewChild(PopupSliderComponent) child:PopupSliderComponent;
@ViewChild("container",{read:ViewContainerRef}) container;
  
  constructor(protected componentResolver: ComponentFactoryResolver,
        protected customComponentBuilder: SliderComponent) {
  }
sliderShow(myphotoId){
    if(this.copom!=null){
      this.copom.destroy();
    }
    console.log(",y iid " + myphotoId);
    this.copom= this.container.createComponent(this.componentResolver.resolveComponentFactory(SliderComponent));
    this.copom.instance.post=this.post.objectIdObj;
    this.copom.instance.photoId=myphotoId;
    this.copom.instance.parent=this.child;

    this.slider.show();
  //slider1
}
  ngOnInit() {
      if(this.post!=null){
       if(this.post.objectIdObj!=null && this.post.objectIdObj.length==1){
          if(this.post.objectIdObj[0].details.length>2){
             this.photo1=this.path+'/'+this.post.objectIdObj[0].createdBy+'/'+this.post.objectIdObj[0].albumId+'/'+this.post.objectIdObj[0].details[2].name;
          }else{
             this.photo1=this.path+'/'+this.post.objectIdObj[0].createdBy+'/'+this.post.objectIdObj[0].albumId+'/'+this.post.objectIdObj[0].details[0].name;
          }
          this.photoid1=this.post.objectIdObj[0].id;

       }else if(this.post.objectIdObj!=null && this.post.objectIdObj.length==2){
          //check the size and adjus the same
          //this.photo1=path+'/'+post?.objectIdObj[0]?.createdBy}}/{{post?.objectIdObj[0]?.albumId}}/{{post?.objectIdObj[0].details[2].name;
            if(this.post.objectIdObj[0].details[0].width>this.post.objectIdObj[0].details[0].height){
              //width is greater
              this.displayType=1;
              this.photo1=this.path+'/'+this.post.objectIdObj[0].createdBy+'/'+this.post.objectIdObj[0].albumId+'/'+this.post.objectIdObj[0].details[2].name;
              this.photo2=this.path+'/'+this.post.objectIdObj[1].createdBy+'/'+this.post.objectIdObj[1].albumId+'/'+this.post.objectIdObj[1].details[2].name;
              this.photoid1=this.post.objectIdObj[0].id;
              this.photoid2=this.post.objectIdObj[1].id;
           }else{
              this.displayType=2;
              //height is greater
            }

            // alert("hi");
        }else if(this.post.objectIdObj!=null && this.post.objectIdObj.length==4){
       
            //check the size and adjus the same
            //this.photo1=path+'/'+post?.objectIdObj[0]?.createdBy}}/{{post?.objectIdObj[0]?.albumId}}/{{post?.objectIdObj[0].details[2].name;
              if(this.post.objectIdObj[0].details[0].width>this.post.objectIdObj[0].details[0].height){
                //width is greater
                this.displayType=1;
                let index=1;
                this.post.objectIdObj.forEach(element => {
                    if(element.details.length>=2){
                      if(index==1){
                          this.photo1=this.path+'/'+this.post.objectIdObj[0].createdBy+'/'+this.post.objectIdObj[0].albumId+'/'+this.post.objectIdObj[0].details[2].name;
                            this.photoid1=this.post.objectIdObj[0].id;
                      }else if(index==2){
                        this.photo2=this.path+'/'+this.post.objectIdObj[1].createdBy+'/'+this.post.objectIdObj[1].albumId+'/'+this.post.objectIdObj[1].details[2].name;
                          this.photoid2=this.post.objectIdObj[1].id;
                      }else if(index==3){
                            
                        this.photo3=this.path+'/'+this.post.objectIdObj[2].createdBy+'/'+this.post.objectIdObj[2].albumId+'/'+this.post.objectIdObj[2].details[2].name;
                          this.photoid3=this.post.objectIdObj[2].id;
                      }else {
                         this.photo4=this.path+'/'+this.post.objectIdObj[3].createdBy+'/'+this.post.objectIdObj[3].albumId+'/'+this.post.objectIdObj[3].details[2].name;
                           this.photoid4=this.post.objectIdObj[3].id;
                      }
                    }else{
                      if(index==1){
                          this.photo1=this.path+'/'+this.post.objectIdObj[0].createdBy+'/'+this.post.objectIdObj[0].albumId+'/'+this.post.objectIdObj[0].details[0].name;
                            this.photoid1=this.post.objectIdObj[0].id;
                      }else if(index==2){
                        this.photo2=this.path+'/'+this.post.objectIdObj[1].createdBy+'/'+this.post.objectIdObj[1].albumId+'/'+this.post.objectIdObj[1].details[0].name;
                          this.photoid2=this.post.objectIdObj[1].id;
                      }else if(index==3){
                            
                        this.photo3=this.path+'/'+this.post.objectIdObj[2].createdBy+'/'+this.post.objectIdObj[2].albumId+'/'+this.post.objectIdObj[2].details[0].name;
                          this.photoid3=this.post.objectIdObj[2].id;
                      }else {
                         this.photo4=this.path+'/'+this.post.objectIdObj[3].createdBy+'/'+this.post.objectIdObj[3].albumId+'/'+this.post.objectIdObj[3].details[0].name;
                           this.photoid4=this.post.objectIdObj[3].id;
                      }
                    }
                    index+=1;
                });
                
                
                
               

              }else{
                this.displayType=2;
                //height is greater
              }

              // alert("hi");
          
        }
      }


  }
  likePost() {

  }

  

}
