package com.stussy.stussy.clone20220929ng.dto.validation;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

// 순서를 여기서 지정하여 줄 수 있다 !
@GroupSequence({Default.class, ValidationGroups.NotBlankGroup.class, ValidationGroups.PatternCheckGroup.class})
public interface ValidationSequence {
}
