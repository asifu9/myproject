import { Pipe, PipeTransform } from '@angular/core';
import { TranslationService } from "../../shared/shared/translate.service";


@Pipe({
    name: 'in18',
    pure: false 
})
export class TranslationPipePipe implements PipeTransform {

    constructor(private translationService: TranslationService) {}

  transform(value: any, args?: any): any {
     return this.translationService.get(value);
  }


}
