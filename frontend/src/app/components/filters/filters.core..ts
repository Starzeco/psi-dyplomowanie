export type DefaultFilterConfig = {
    name: string,
    labelKey: string,
    defaultValue?: string,
  }
  
  export abstract class DefaultFilterComponent {
    abstract clear(): void;
  }
  
  export type DefaultFilterEvent = { source: string, value: unknown }
  
