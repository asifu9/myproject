import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LeftPanelComponent } from './left-panel/left-panel.component';
import { TopPanelComponent } from './top-panel/top-panel.component';
import { RightPanelComponent } from './right-panel/right-panel.component';

import { LoaderComponent } from './shared/loader/loader.component';
import { SuccessComponent } from './shared/success/success.component';
import { FailureComponent } from './shared/failure/failure.component';
import { ShareComponent } from './shared/share/share.component';
import { PopupComponent } from './shared/popup/popup.component';
import { PopupSliderComponent } from './shared/popup/popupslider.component';
import { PopInlineComponent } from './shared/popup/popInline.component';
import { DropdownComponent } from './shared/dropdown/dropdown.component';
import { FileUploadComponent } from './shared/file-upload/file-upload.component';
import { SearchtopComponent } from './searchtop/searchtop.component';
import { MyDialogComponent } from './shared/my-dialog/my-dialog.component';
import { UserpopComponent } from './shared/userpop/userpop.component';
import { MesdetailComponent } from "../messages/mesdetail/mesdetail.component";
import { PostShareComponent } from "../feed/postshare.component";
import { FeedModule } from "../feed/feed.module";
import { RouterModule } from "@angular/router";
import { FormsModule } from "@angular/forms";
import { BrowserModule } from '@angular/platform-browser';
import { Pipe } from '@angular/core';
import { PhotoModule } from "../photo/photo.module";
import { HttpModule } from "@angular/http";
import { HttpClientModule } from "@angular/common/http";
import { FileUploadModule } from "primeng/components/fileupload/fileupload";
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AccessRestrictionComponent } from "./access-restriction/access-restriction.component";
import { ReportComponent } from "./shared/report/report.component";
import { ReportViewComponent } from "./shared/report/report-view/report-view.component";
import { ReportFilterComponent } from "./shared/report/report-filter/report-filter.component";
import { FilterTypeComponent } from "./shared/report/filter-type/filter-type.component";
import { ReportFilterPipe } from "./shared/report/ReportFilterPipe";
import { ReportSortPipe } from "./shared/report/ReportSortPipe";
import { LeavesModule } from "../leaves/leaves.module";
import { ScrollTopService } from "./shared/scroll-top-service.service";
import { NotifiMenuComponent } from '../shared/shared/notifi-menu/notifi-menu.component';
import { NotificationListComponent } from '../shared/shared/notification-list/notification-list.component';
import { ModalPopupComponent } from '../shared/shared/modal-popup/modal-popup.component';
import { CalendarModule } from 'primeng/primeng';
import {MatButtonModule, MatCheckboxModule,MatTableDataSource,  MatFormFieldModule, 
    MatInputModule, 
    MatPaginatorModule, 
    MatTableModule,
    MatSortModule, 
    MatChipsModule,
    MatToolbarModule,
    MatGridListModule,
    MatRadioModule,
    MatMenuModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSnackBarModule,
    MatProgressBarModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatCardModule,
    MatDialogActions,
    MatDialogContent,
    MatSlideToggleModule,
    MatDialogModule} from '@angular/material';
import { ReportDataComponent } from './shared/report-data/report-data.component';
import { DeleteConfirmationComponent } from './shared/delete-confirmation/delete-confirmation.component';
import { SaveConfirmationComponent } from './shared/save-confirmation/save-confirmation.component';
import { ProgressBarComponent } from './shared/progress-bar/progress-bar.component';
import { ErrorDialogComponent } from './shared/error-dialog/error-dialog.component';
import { TranslationPipePipe } from './shared/translation-pipe.pipe';
import { TranslationService } from "../shared/shared/translate.service";
import { InlinePogressComponent } from './shared/inline-pogress/inline-pogress.component';
import { UserSelectComponent } from './shared/user-select/user-select.component';
import { ContextMenuComponent } from './shared/context-menu/context-menu.component';
@NgModule({
  imports: [
    CommonModule,
      MatFormFieldModule, 
    MatInputModule, 
    MatPaginatorModule, 
    MatDialogModule,
    MatTableModule, 
    MatMenuModule,
    MatSortModule ,
    CalendarModule,
    MatChipsModule,
    RouterModule,
    MatButtonModule, 
    MatCheckboxModule,
    FileUploadModule,
    FormsModule,MatProgressBarModule,
    BrowserModule,
    FormsModule,
    MatProgressSpinnerModule,
    HttpModule,
    HttpClientModule,

    ReactiveFormsModule,
    BrowserAnimationsModule,
       MatToolbarModule,
    MatGridListModule,
    MatFormFieldModule,
    MatInputModule,
    MatRadioModule,
    MatSelectModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatButtonModule,
    MatSnackBarModule,
    MatTableModule,
    MatIconModule,
    MatPaginatorModule,
    MatSortModule,
    MatCardModule
  ],
  declarations: [LeftPanelComponent,
    TopPanelComponent,
    RightPanelComponent,
    LoaderComponent,
    SuccessComponent,
    FailureComponent,
    ShareComponent,
    PopupComponent,
    PopupSliderComponent,
    PopInlineComponent,
    DropdownComponent,
    FileUploadComponent,
    SearchtopComponent,
    MyDialogComponent,
    UserpopComponent,
    AccessRestrictionComponent,
    ReportComponent,
    ReportViewComponent,
    ReportFilterComponent,
    FilterTypeComponent,
    ReportFilterPipe,
    ReportSortPipe,
    NotifiMenuComponent,
    NotificationListComponent,
    ModalPopupComponent,
    ReportDataComponent,
    DeleteConfirmationComponent,
    SaveConfirmationComponent,
    ProgressBarComponent,
    ErrorDialogComponent,
    TranslationPipePipe,
    InlinePogressComponent,
    UserSelectComponent,
    ContextMenuComponent
    

  ],
  exports:
  [
    CommonModule,
    RouterModule,
    ErrorDialogComponent,
      MatFormFieldModule, 
    MatInputModule, 
    MatPaginatorModule, 
    InlinePogressComponent,
    MatChipsModule,
    MatProgressBarModule,
    MatTableModule, 
    MatSlideToggleModule,
    UserSelectComponent,
    DeleteConfirmationComponent,
    MatSortModule,
    BrowserAnimationsModule,
    FileUploadModule,
    FormsModule,
    MatButtonModule, 
    MatCheckboxModule,
    BrowserModule,
    ReactiveFormsModule,
    MatMenuModule,
    ContextMenuComponent,
    FormsModule,
    LeftPanelComponent,
    TopPanelComponent,
    CalendarModule,
    RightPanelComponent,
    LoaderComponent,
    SuccessComponent,
    FailureComponent,
    ShareComponent,
    PopupComponent,
    PopupSliderComponent,
    PopInlineComponent,
    DropdownComponent,
    FileUploadComponent,
    SearchtopComponent,
    MyDialogComponent,
    UserpopComponent,
    AccessRestrictionComponent,
    ReportComponent,
    ReportViewComponent,
    TranslationPipePipe,
    ReportFilterComponent,
    FilterTypeComponent,
    ReportDataComponent,
        MatToolbarModule,
    MatGridListModule,
    MatFormFieldModule,
    MatInputModule,
    MatRadioModule,
    MatSelectModule,
    MatCardModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatTableModule,
    MatIconModule,
    MatDialogModule,
    ProgressBarComponent,
    MatPaginatorModule,
    MatSortModule,
  ],
  providers:[TranslationService],
  entryComponents: [FileUploadComponent,UserSelectComponent,ErrorDialogComponent,ProgressBarComponent,SaveConfirmationComponent, UserpopComponent, MesdetailComponent, PostShareComponent, MyDialogComponent,DeleteConfirmationComponent]
})
export class SharedModule { }
