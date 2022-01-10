import { Directive, Input } from "@angular/core";
import { NG_VALIDATORS, Validator, AbstractControl, ValidatorFn } from "@angular/forms";

export function startsWithValidator(startString: string): ValidatorFn {
  return (control: AbstractControl): {[key: string]: any} => {
    if (!control.value) {
      return null;
    }
    const isStarting = 0 === control.value.indexOf(startString);
    return isStarting ? null : {'validateStartsWith': {value: control.value}};
  };
}

@Directive({
  selector: '[validateStartsWith]',
  providers: [{provide: NG_VALIDATORS, useExisting: ValidateStartsWithDirective, multi: true}]
})
export class ValidateStartsWithDirective implements Validator {
  @Input('validateStartsWith') startsWith: string;

  public validate(control: AbstractControl): {[key: string]: any} {
    return this.startsWith ? startsWithValidator(this.startsWith)(control)
      : null;
  }

  
}