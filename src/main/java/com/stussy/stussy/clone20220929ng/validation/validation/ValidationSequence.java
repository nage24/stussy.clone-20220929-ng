package com.stussy.stussy.clone20220929ng.validation.validation;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

// 순서를 여기서 지정하여 줄 수 있다 ! ; 우선순위
@GroupSequence({
        Default.class,
        ValidationGroups.NotBlankGroup.class,
        ValidationGroups.SizeCheckGroup.class,
        ValidationGroups.PatternCheckGroup.class
})
public interface ValidationSequence {
}
