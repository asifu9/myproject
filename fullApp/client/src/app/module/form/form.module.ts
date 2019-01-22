import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormViewComponent } from '../form/form-view/form-view.component';
import { WidgetInputfieldComponent } from '../form/widgets/widget-inputfield/widget-inputfield.component';
import { WidgetWrapperComponent } from '../form/widgets/widget-wrapper.component';
import { SharedModule } from "../shared/shared.module";
import { FormStateService } from "./widgets/form-state.service";
import { WidgetDateComponent } from './widgets/widget-date/widget-date.component';
import { WidgetCheckboxComponent } from './widgets/widget-checkbox/widget-checkbox.component';
import { WidgetDropdownComponent } from './widgets/widget-dropdown/widget-dropdown.component';
import { WidgetMultichoiceComponent } from './widgets/widget-multichoice/widget-multichoice.component';
import { WidgetListComponent } from './widgets/widget-list/widget-list.component';
import { WidgetTextareaComponent } from './widgets/widget-textarea/widget-textarea.component';
import { WidgetBase } from "./widgets/widget-base";
import { WidgetSelectComponent } from './widgets/widget-select/widget-select.component';
import { WidgetAttachComponent } from './widgets/widget-attach/widget-attach.component';
import { WidgetTableComponent } from './widgets/widget-table/widget-table.component';
import { WidgetTableInputfieldComponent } from "./widgets/widget-table/widget-table-inputfield/widget-table-inputfield.component";
import { WidgetTableDateComponent } from "./widgets/widget-table/widget-table-date/widget-table-date.component";
import { WidgetTableCheckboxComponent } from "./widgets/widget-table/widget-table-checkbox/widget-table-checkbox.component";
import { WidgetTableDropdownComponent } from "./widgets/widget-table/widget-table-dropdown/widget-table-dropdown.component";
import { WidgetTableMultichoiceComponent } from "./widgets/widget-table/widget-table-multichoice/widget-table-multichoice.component";
import { WidgetTableListComponent } from "./widgets/widget-table/widget-table-list/widget-table-list.component";
import { WidgetTableTextareaComponent } from "./widgets/widget-table/widget-table-textarea/widget-table-textarea.component";
import { WidgetTableSelectComponent } from "./widgets/widget-table/widget-table-select/widget-table-select.component";
import { WidgetTableAttachComponent } from "./widgets/widget-table/widget-table-attach/widget-table-attach.component";

@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [FormViewComponent, 
    
    WidgetWrapperComponent, 
    WidgetBase,
    WidgetDateComponent, 
    WidgetCheckboxComponent, 
    WidgetDropdownComponent, 
    WidgetMultichoiceComponent, 
    WidgetListComponent, 
    WidgetTextareaComponent, 
    WidgetSelectComponent, 
    WidgetAttachComponent, 
    WidgetInputfieldComponent, 
    WidgetTableComponent,
    WidgetTableDateComponent, 
    WidgetTableCheckboxComponent, 
    WidgetTableDropdownComponent, 
    WidgetTableMultichoiceComponent, 
    WidgetTableListComponent, 
    WidgetTableTextareaComponent, 
    WidgetTableSelectComponent, 
    WidgetTableAttachComponent, 
    WidgetTableInputfieldComponent, 
    ],
  exports:[FormViewComponent,WidgetInputfieldComponent,WidgetWrapperComponent,WidgetBase],
  providers:[FormStateService]
})
export class FormModule { }
