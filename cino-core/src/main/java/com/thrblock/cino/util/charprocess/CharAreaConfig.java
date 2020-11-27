package com.thrblock.cino.util.charprocess;

import java.awt.Font;
import java.util.function.Consumer;

import com.thrblock.cino.glshape.GLImage;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.cino.gltexture.FontsInCommon;
import com.thrblock.cino.gltexture.GLFont;
import com.thrblock.cino.lnode.LNode;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharAreaConfig {
    private static final GLFont DEFAULT_FONT = new GLFont(new Font(FontsInCommon.GNU_FREE_MONO, Font.PLAIN, 12));
    private final char[] charArray;
    @Builder.Default
    private GLFont font = DEFAULT_FONT;
    @Builder.Default
    private PositionSynchronizer positionSyn = new FlowAsLine();
    @Builder.Default
    private CharStyle style = (arr, i, img) -> {
    };
    private LNode node;
    @Builder.Default
    private Consumer<GLRect> rectStyle = r -> r.setDisplay(false);

    public void setSimpleStyle(Consumer<GLImage> st) {
        this.style = (arr, i, img) -> st.accept(img);
    }
}
