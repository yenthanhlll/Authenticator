/*     */ package WEB-INF.classes.com.dreammirae.mmth.web.filter;
/*     */ 
/*     */ import java.util.regex.Pattern;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletRequestWrapper;
/*     */ 
/*     */ public class XSSRequestWrapper
/*     */   extends HttpServletRequestWrapper {
/*   9 */   private static Pattern[] patterns = new Pattern[] {
/*     */ 
/*     */       
/*  12 */       Pattern.compile("<script>(.*?)</script>", 2), 
/*  13 */       Pattern.compile("<iframe>(.*?)</iframe>", 2), 
/*  14 */       Pattern.compile("<embed>(.*?)</embed>", 2), 
/*     */ 
/*     */       
/*  17 */       Pattern.compile("src[\r\n]*=[\r\n]*\\'(.*?)\\'", 42), 
/*  18 */       Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", 42), 
/*     */ 
/*     */       
/*  21 */       Pattern.compile("</script>", 2), 
/*  22 */       Pattern.compile("<script(.*?)>", 42), 
/*     */       
/*  24 */       Pattern.compile("</iframe>", 2), 
/*  25 */       Pattern.compile("<iframe(.*?)>", 42), 
/*     */       
/*  27 */       Pattern.compile("</embed>", 2), 
/*  28 */       Pattern.compile("<embed(.*?)>", 42), 
/*     */ 
/*     */       
/*  31 */       Pattern.compile("eval\\((.*?)\\)", 42), 
/*     */       
/*  33 */       Pattern.compile("expression\\((.*?)\\)", 42), 
/*     */       
/*  35 */       Pattern.compile("javascript:", 2), 
/*     */       
/*  37 */       Pattern.compile("vbscript:", 2), 
/*     */       
/*  39 */       Pattern.compile("onload(.*?)=", 42), 
/*     */ 
/*     */       
/*  42 */       Pattern.compile("FSCommand", 42), 
/*  43 */       Pattern.compile("onAbort", 42), 
/*  44 */       Pattern.compile("onActivate", 42), 
/*  45 */       Pattern.compile("onAfterPrint", 42), 
/*  46 */       Pattern.compile("onAfterUpdate", 42), 
/*  47 */       Pattern.compile("onBeforeActivate", 42), 
/*  48 */       Pattern.compile("onBeforeCopy", 42), 
/*  49 */       Pattern.compile("onBeforeCut", 42), 
/*  50 */       Pattern.compile("onBeforeDeactivate", 42), 
/*  51 */       Pattern.compile("onBeforeEditFocus", 42), 
/*  52 */       Pattern.compile("onBeforePaste", 42), 
/*  53 */       Pattern.compile("onBeforePrint", 42), 
/*  54 */       Pattern.compile("onBeforeUnload", 42), 
/*  55 */       Pattern.compile("onBeforeUpdate", 42), 
/*  56 */       Pattern.compile("onBegin", 42), 
/*  57 */       Pattern.compile("onBlur", 42), 
/*  58 */       Pattern.compile("onBounce", 42), 
/*  59 */       Pattern.compile("onCellChange", 42), 
/*  60 */       Pattern.compile("onChange", 42), 
/*  61 */       Pattern.compile("onClick", 42), 
/*  62 */       Pattern.compile("onContextMenu", 42), 
/*  63 */       Pattern.compile("onControlSelect", 42), 
/*  64 */       Pattern.compile("onCopy", 42), 
/*  65 */       Pattern.compile("onCut", 42), 
/*  66 */       Pattern.compile("onDataAvailable", 42), 
/*  67 */       Pattern.compile("onDataSetChanged", 42), 
/*  68 */       Pattern.compile("onDataSetComplete", 42), 
/*  69 */       Pattern.compile("onDblClick", 42), 
/*  70 */       Pattern.compile("onDeactivate", 42), 
/*  71 */       Pattern.compile("onDrag", 42), 
/*  72 */       Pattern.compile("onDragEnd", 42), 
/*  73 */       Pattern.compile("onDragLeave", 42), 
/*  74 */       Pattern.compile("onDragEnter", 42), 
/*  75 */       Pattern.compile("onDragOver", 42), 
/*  76 */       Pattern.compile("onDragDrop", 42), 
/*  77 */       Pattern.compile("onDragStart", 42), 
/*  78 */       Pattern.compile("onDrop", 42), 
/*  79 */       Pattern.compile("onEnd", 42), 
/*  80 */       Pattern.compile("onError", 42), 
/*  81 */       Pattern.compile("onErrorUpdate", 42), 
/*  82 */       Pattern.compile("onFilterChange", 42), 
/*  83 */       Pattern.compile("onFinish", 42), 
/*  84 */       Pattern.compile("onFocus", 42), 
/*  85 */       Pattern.compile("onFocusIn", 42), 
/*  86 */       Pattern.compile("onFocusOut", 42), 
/*  87 */       Pattern.compile("onHashChange", 42), 
/*  88 */       Pattern.compile("onHelp", 42), 
/*  89 */       Pattern.compile("onInput", 42), 
/*  90 */       Pattern.compile("onKeyDown", 42), 
/*  91 */       Pattern.compile("onKeyPress", 42), 
/*  92 */       Pattern.compile("onKeyUp", 42), 
/*  93 */       Pattern.compile("onLayoutComplete", 42), 
/*  94 */       Pattern.compile("onLoad", 42), 
/*  95 */       Pattern.compile("onLoseCapture", 42), 
/*  96 */       Pattern.compile("onMediaComplete", 42), 
/*  97 */       Pattern.compile("onMediaError", 42), 
/*  98 */       Pattern.compile("onMessage", 42), 
/*  99 */       Pattern.compile("onMouseDown", 42), 
/* 100 */       Pattern.compile("onMouseEnter", 42), 
/* 101 */       Pattern.compile("onMouseLeave", 42), 
/* 102 */       Pattern.compile("onMouseMove", 42), 
/* 103 */       Pattern.compile("onMouseOut", 42), 
/* 104 */       Pattern.compile("onMouseOver", 42), 
/* 105 */       Pattern.compile("onMouseUp", 42), 
/* 106 */       Pattern.compile("onMouseWheel", 42), 
/* 107 */       Pattern.compile("onMove", 42), 
/* 108 */       Pattern.compile("onMoveEnd", 42), 
/* 109 */       Pattern.compile("onMoveStart", 42), 
/* 110 */       Pattern.compile("onOffline", 42), 
/* 111 */       Pattern.compile("onOnline", 42), 
/* 112 */       Pattern.compile("onOutOfSync", 42), 
/* 113 */       Pattern.compile("onPaste", 42), 
/* 114 */       Pattern.compile("onPause", 42), 
/* 115 */       Pattern.compile("onPopState", 42), 
/* 116 */       Pattern.compile("onProgress", 42), 
/* 117 */       Pattern.compile("onPropertyChange", 42), 
/* 118 */       Pattern.compile("onReadyStateChange", 42), 
/* 119 */       Pattern.compile("onRedo", 42), 
/* 120 */       Pattern.compile("onRepeat", 42), 
/* 121 */       Pattern.compile("onReset", 42), 
/* 122 */       Pattern.compile("onResize", 42), 
/* 123 */       Pattern.compile("onResizeEnd", 42), 
/* 124 */       Pattern.compile("onResizeStart", 42), 
/* 125 */       Pattern.compile("onResume", 42), 
/* 126 */       Pattern.compile("onReverse", 42), 
/* 127 */       Pattern.compile("onRowsEnter", 42), 
/* 128 */       Pattern.compile("onRowExit", 42), 
/* 129 */       Pattern.compile("onRowDelete", 42), 
/* 130 */       Pattern.compile("onRowInserted", 42), 
/* 131 */       Pattern.compile("onScroll", 42), 
/* 132 */       Pattern.compile("onSeek", 42), 
/* 133 */       Pattern.compile("onSelect", 42), 
/* 134 */       Pattern.compile("onSelectionChange", 42), 
/* 135 */       Pattern.compile("onSelectStart", 42), 
/* 136 */       Pattern.compile("onStart", 42), 
/* 137 */       Pattern.compile("onStop", 42), 
/* 138 */       Pattern.compile("onStorage", 42), 
/* 139 */       Pattern.compile("onSyncRestored", 42), 
/* 140 */       Pattern.compile("onSubmit", 42), 
/* 141 */       Pattern.compile("onTimeError", 42), 
/* 142 */       Pattern.compile("onTrackChange", 42), 
/* 143 */       Pattern.compile("onUndo", 42), 
/* 144 */       Pattern.compile("onUnload", 42), 
/* 145 */       Pattern.compile("onURLFlip", 42), 
/* 146 */       Pattern.compile("seekSegmentTime", 42), 
/*     */       
/* 148 */       Pattern.compile("alert", 42), 
/* 149 */       Pattern.compile("document", 42)
/*     */     };
/*     */ 
/*     */   
/*     */   public XSSRequestWrapper(HttpServletRequest servletRequest) {
/* 154 */     super(servletRequest);
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getParameterValues(String parameter) {
/* 159 */     String[] values = super.getParameterValues(parameter);
/*     */     
/* 161 */     if (values == null) {
/* 162 */       return null;
/*     */     }
/*     */     
/* 165 */     int count = values.length;
/* 166 */     String[] encodedValues = new String[count];
/* 167 */     for (int i = 0; i < count; i++) {
/* 168 */       encodedValues[i] = stripXSS(values[i]);
/*     */     }
/*     */     
/* 171 */     return encodedValues;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getParameter(String parameter) {
/* 176 */     String value = super.getParameter(parameter);
/*     */     
/* 178 */     return stripXSS(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHeader(String name) {
/* 183 */     String value = super.getHeader(name);
/* 184 */     return stripXSS(value);
/*     */   }
/*     */   
/*     */   private String stripXSS(String value) {
/* 188 */     if (value != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 194 */       value = value.replaceAll("\000", "");
/*     */ 
/*     */       
/* 197 */       for (Pattern scriptPattern : patterns) {
/* 198 */         value = scriptPattern.matcher(value).replaceAll("");
/*     */       }
/*     */     } 
/* 201 */     return value;
/*     */   }
/*     */ }


/* Location:              D:\Authenticator\Authenticator-main\ROOT.jar!\WEB-INF\classes\com\dreammirae\mmth\web\filter\XSSRequestWrapper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */