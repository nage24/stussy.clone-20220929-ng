package com.stussy.stussy.clone20220929ng.dto.validation;

import javax.validation.GroupSequence;

// 순서를 여기서 지정하여 줄 수 있다 !
public interface ValidationSequence {

    @GroupSequence({Default.class, ValidationGroup.NotBlankGroup.class, ValidationGroup.PatternCheckGroup.class})
    public interface ValidationSequence {
    }

}
