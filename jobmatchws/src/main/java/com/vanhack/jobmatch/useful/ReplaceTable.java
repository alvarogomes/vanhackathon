package com.vanhack.jobmatch.useful;

import com.aspose.words.CompositeNode;
import com.aspose.words.Document;
import com.aspose.words.IReplacingCallback;
import com.aspose.words.Node;
import com.aspose.words.NodeType;
import com.aspose.words.Paragraph;
import com.aspose.words.ReplaceAction;
import com.aspose.words.ReplacingArgs;
import com.aspose.words.Table;

public class ReplaceTable implements IReplacingCallback{
	private Document doc;
	private Table table;
	
	public ReplaceTable(Document doc, Table table) {
		super();
		this.doc = doc;
		this.table = table;
	}
	
	@Override
	public int replacing(ReplacingArgs e) throws Exception {
		
		Paragraph para = (Paragraph) e.getMatchNode().getParentNode();
		insertItem(para, table);
		
		para.remove();
		return ReplaceAction.SKIP;
	}
	
	private void insertItem(Node insertAfterNode, Node novoNo) throws Exception {
		// Make sure that the node is either a paragraph or table.
		if ((insertAfterNode.getNodeType() != NodeType.PARAGRAPH) & (insertAfterNode.getNodeType() != NodeType.TABLE)) {
			throw new IllegalArgumentException("The destination node should be either a paragraph or table.");
		}

		// We will be inserting into the parent of the destination paragraph.
		CompositeNode dstStory = insertAfterNode.getParentNode();

		dstStory.insertAfter(novoNo, insertAfterNode);

	}
}
